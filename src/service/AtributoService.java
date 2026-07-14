package service;

import model.Personagem;

/**
 * Responsabilidade única: cálculos de regras de D&D 5e relacionados a
 * atributos (modificadores, bônus de proficiência, bônus de ataque).
 *
 * Code smell corrigido: no PanelCharacterSheet original, valores como
 * "BÔNUS DE ATAQUE: +5" e "Atletismo +6" estavam escritos como texto fixo
 * (números "chutados" direto na tela, sem relação nenhuma com os atributos
 * reais do personagem). Essa classe calcula esses valores de verdade a
 * partir dos atributos e do nível, então a tela apenas exibe o resultado.
 */
public class AtributoService {

    public int calcularModificador(int valorAtributo) {
        return (int) Math.floor((valorAtributo - 10) / 2.0);
    }

    public int calcularBonusProficiencia(int nivel) {
        // Regra de D&D 5e: bônus de proficiência cresce a cada 4 níveis, começando em +2
        return 2 + ((Math.max(nivel, 1) - 1) / 4);
    }

    public int calcularBonusAtaque(Personagem p) {
        return calcularModificador(p.getForca()) + calcularBonusProficiencia(p.getNivel());
    }

    public int calcularBonusPericia(Personagem p, String atributoBase) {
        int modificador;
        switch (atributoBase.toLowerCase()) {
            case "forca": modificador = calcularModificador(p.getForca()); break;
            case "destreza": modificador = calcularModificador(p.getDestreza()); break;
            case "constituicao": modificador = calcularModificador(p.getConstituicao()); break;
            case "inteligencia": modificador = calcularModificador(p.getInteligencia()); break;
            case "sabedoria": modificador = calcularModificador(p.getSabedoria()); break;
            case "carisma": modificador = calcularModificador(p.getCarisma()); break;
            default: modificador = 0;
        }
        return modificador + calcularBonusProficiencia(p.getNivel());
    }

    public String formatarModificador(int valor) {
        return valor >= 0 ? "+" + valor : String.valueOf(valor);
    }
}
