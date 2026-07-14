package service;

import model.Personagem;
import repository.PersonagemRepository;
import repository.RepositoryException;

import java.util.List;

/**
 * Responsabilidade única: regras de negócio de Personagem.
 *
 * Reúne validações e valores-padrão que, no projeto original, estavam
 * espalhados entre CadastroPersonagemVIEW.salvar() (validação de nome,
 * parsing de números) e PanelCharacterSheet.carregarDados() (criação de
 * um personagem "padrão" quando a lista está vazia).
 */
public class PersonagemService {
    private static final int ATRIBUTO_MINIMO = 1;
    private static final int ATRIBUTO_MAXIMO = 30;

    private final PersonagemRepository repository;

    public PersonagemService(PersonagemRepository repository) {
        this.repository = repository;
    }

    public List<Personagem> listar() throws ServiceException {
        try {
            return repository.listar();
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível carregar os personagens.", e);
        }
    }

    public Personagem buscarPorId(int id) throws ServiceException {
        try {
            return repository.buscarPorId(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível buscar o personagem " + id, e);
        }
    }

    /**
     * Retorna o primeiro personagem cadastrado ou, se não houver nenhum,
     * um personagem padrão em memória (ainda não salvo no banco).
     * Extraído de PanelCharacterSheet.carregarDados(), que antes fazia
     * essa criação de "personagem padrão" diretamente na tela.
     */
    public Personagem obterPersonagemAtualOuPadrao() throws ServiceException {
        List<Personagem> lista = listar();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return criarPersonagemPadrao();
    }

    private Personagem criarPersonagemPadrao() {
        Personagem p = new Personagem();
        p.setNome("Novo Personagem");
        p.setClasse("Guerreiro");
        p.setNivel(1);
        p.setRaca("Humano");
        p.setAlinhamento("Neutro");
        p.setForca(10);
        p.setDestreza(10);
        p.setConstituicao(10);
        p.setInteligencia(10);
        p.setSabedoria(10);
        p.setCarisma(10);
        p.setPvMax(10);
        p.setPvAtual(10);
        p.setCa(10);
        p.setNotas("");
        return p;
    }

    /**
     * Cria e persiste um novo personagem a partir dos dados brutos vindos
     * da tela de cadastro. Toda a validação (nome obrigatório, números
     * válidos, atributos dentro da faixa 1-30) foi extraída daqui de
     * CadastroPersonagemVIEW.salvar().
     */
    public Personagem criar(String nome, String classe, String nivelStr, String raca, String alinhamento,
                             String forcaStr, String destrezaStr, String constituicaoStr, String inteligenciaStr,
                             String sabedoriaStr, String carismaStr, String pvMaxStr, String caStr,
                             Integer campanhaId) throws ServiceException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new ServiceException("O nome do personagem é obrigatório.");
        }

        Personagem p = new Personagem();
        p.setNome(nome.trim());
        p.setClasse(classe);
        p.setNivel(parseInteiroPositivo(nivelStr, "Nível"));
        p.setRaca(raca);
        p.setAlinhamento(alinhamento);
        p.setForca(validarAtributo(parseInteiro(forcaStr, "Força")));
        p.setDestreza(validarAtributo(parseInteiro(destrezaStr, "Destreza")));
        p.setConstituicao(validarAtributo(parseInteiro(constituicaoStr, "Constituição")));
        p.setInteligencia(validarAtributo(parseInteiro(inteligenciaStr, "Inteligência")));
        p.setSabedoria(validarAtributo(parseInteiro(sabedoriaStr, "Sabedoria")));
        p.setCarisma(validarAtributo(parseInteiro(carismaStr, "Carisma")));
        int pvMax = parseInteiroPositivo(pvMaxStr, "PV Máximo");
        p.setPvMax(pvMax);
        p.setPvAtual(pvMax); // regra: personagem novo começa com PV atual = PV máximo
        p.setCa(parseInteiroPositivo(caStr, "Classe de Armadura"));
        p.setNotas("");
        if (campanhaId != null && campanhaId > 0) {
            p.setCampanhaId(campanhaId);
        }

        try {
            repository.inserir(p);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível salvar o personagem.", e);
        }
        return p;
    }

    public void atualizarNotas(Personagem p, String notas) throws ServiceException {
        p.setNotas(notas);
        try {
            repository.atualizar(p);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível salvar as notas.", e);
        }
    }

    private int parseInteiro(String valor, String campo) throws ServiceException {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            throw new ServiceException("O campo '" + campo + "' deve ser um número válido.");
        }
    }

    private int parseInteiroPositivo(String valor, String campo) throws ServiceException {
        int v = parseInteiro(valor, campo);
        if (v < 0) {
            throw new ServiceException("O campo '" + campo + "' não pode ser negativo.");
        }
        return v;
    }

    private int validarAtributo(int valor) throws ServiceException {
        if (valor < ATRIBUTO_MINIMO || valor > ATRIBUTO_MAXIMO) {
            throw new ServiceException("Atributos devem estar entre " + ATRIBUTO_MINIMO + " e " + ATRIBUTO_MAXIMO + ".");
        }
        return valor;
    }
}
