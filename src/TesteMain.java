import model.Personagem;
import service.AtributoService;
import service.CampanhaService;
import service.PersonagemService;
import service.ServiceException;
import service.ServiceFactory;

/**
 * Bateria de testes manuais executados via main(), conforme pedido na
 * Etapa 6. Não é um framework de testes (JUnit já foi usado na UC12);
 * aqui o objetivo é demonstrar, de forma simples e legível no console,
 * que a camada de serviço (regras de negócio) funciona corretamente
 * mesmo sem abrir nenhuma tela Swing — prova de que a separação de
 * responsabilidades feita nesta etapa realmente funciona.
 */
public class TesteMain {

    private static int testesOk = 0;
    private static int testesFalhou = 0;

    public static void main(String[] args) {
        System.out.println("=== TESTES: AtributoService (cálculo de regras D&D) ===");
        testarCalculoDeModificadores();
        testarBonusProficiencia();

        System.out.println("\n=== TESTES: PersonagemService (validações de negócio) ===");
        testarValidacaoNomeObrigatorio();
        testarValidacaoAtributoForaDaFaixa();
        testarValidacaoNumeroInvalido();

        System.out.println("\n=== TESTES: CampanhaService (validações de negócio) ===");
        testarValidacaoNomeCampanhaObrigatorio();

        System.out.println("\n=== TESTE OPCIONAL DE INTEGRAÇÃO (requer MySQL configurado) ===");
        testarIntegracaoComBanco();

        System.out.println("\n----------------------------------------");
        System.out.println("Resultado final: " + testesOk + " passaram, " + testesFalhou + " falharam.");
    }

    private static void testarCalculoDeModificadores() {
        AtributoService service = ServiceFactory.getAtributoService();
        // Regra D&D: modificador = floor((atributo - 10) / 2)
        verificar("Modificador de FOR 16 deve ser +3", service.calcularModificador(16) == 3);
        verificar("Modificador de FOR 10 deve ser 0", service.calcularModificador(10) == 0);
        verificar("Modificador de FOR 8 deve ser -1", service.calcularModificador(8) == -1);
        verificar("Modificador de FOR 20 deve ser +5", service.calcularModificador(20) == 5);
    }

    private static void testarBonusProficiencia() {
        AtributoService service = ServiceFactory.getAtributoService();
        verificar("Bônus de proficiência nível 1 deve ser +2", service.calcularBonusProficiencia(1) == 2);
        verificar("Bônus de proficiência nível 5 deve ser +3", service.calcularBonusProficiencia(5) == 3);
        verificar("Bônus de proficiência nível 17 deve ser +6", service.calcularBonusProficiencia(17) == 6);
    }

    private static void testarValidacaoNomeObrigatorio() {
        PersonagemService service = ServiceFactory.getPersonagemService();
        try {
            service.criar("", "Guerreiro", "1", "Humano", "Neutro",
                    "10", "10", "10", "10", "10", "10", "10", "10", null);
            verificar("Deveria ter lançado ServiceException para nome vazio", false);
        } catch (ServiceException e) {
            verificar("Nome vazio é rejeitado corretamente (" + e.getMessage() + ")", true);
        }
    }

    private static void testarValidacaoAtributoForaDaFaixa() {
        PersonagemService service = ServiceFactory.getPersonagemService();
        try {
            // Força = 99 é inválida (fora da faixa 1-30 do D&D 5e)
            service.criar("Aragorn Teste", "Patrulheiro", "5", "Humano", "Neutro",
                    "99", "10", "10", "10", "10", "10", "10", "10", null);
            verificar("Deveria ter lançado ServiceException para atributo=99", false);
        } catch (ServiceException e) {
            verificar("Atributo fora da faixa é rejeitado corretamente (" + e.getMessage() + ")", true);
        }
    }

    private static void testarValidacaoNumeroInvalido() {
        PersonagemService service = ServiceFactory.getPersonagemService();
        try {
            service.criar("Aragorn Teste", "Patrulheiro", "cinco", "Humano", "Neutro",
                    "10", "10", "10", "10", "10", "10", "10", "10", null);
            verificar("Deveria ter lançado ServiceException para nível não-numérico", false);
        } catch (ServiceException e) {
            verificar("Texto não-numérico em campo numérico é rejeitado (" + e.getMessage() + ")", true);
        }
    }

    private static void testarValidacaoNomeCampanhaObrigatorio() {
        CampanhaService service = ServiceFactory.getCampanhaService();
        try {
            service.criar("   ", "Uma descrição qualquer", "Mestre Teste");
            verificar("Deveria ter lançado ServiceException para nome de campanha vazio", false);
        } catch (ServiceException e) {
            verificar("Nome de campanha vazio/só espaços é rejeitado (" + e.getMessage() + ")", true);
        }
    }

    private static void testarIntegracaoComBanco() {
        try {
            PersonagemService service = ServiceFactory.getPersonagemService();
            Personagem atual = service.obterPersonagemAtualOuPadrao();
            System.out.println("[OK] Conectou ao banco e obteve/gerou personagem: " + atual.getNome());
            testesOk++;
        } catch (ServiceException e) {
            System.out.println("[AVISO] Não foi possível conectar ao MySQL para o teste de integração: "
                    + e.getMessage());
            System.out.println("        Isso é esperado se o MySQL local não estiver rodando neste momento.");
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            System.out.println("[OK]    " + descricao);
            testesOk++;
        } else {
            System.out.println("[FALHOU] " + descricao);
            testesFalhou++;
        }
    }
}
