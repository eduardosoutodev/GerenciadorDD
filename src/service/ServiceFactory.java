package service;

import repository.jdbc.CampanhaRepositoryJDBC;
import repository.jdbc.CriaturaRepositoryJDBC;
import repository.jdbc.ItemRepositoryJDBC;
import repository.jdbc.PersonagemRepositoryJDBC;

/**
 * Padrão de projeto: Factory (fábrica simples).
 *
 * Centraliza a criação dos serviços e a "amarração" com suas respectivas
 * implementações de repositório. No projeto original, cada tela fazia
 * "new CampanhaDAO()", "new PersonagemDAO()" etc. diretamente — se um dia
 * fosse necessário trocar a implementação (ex: de JDBC para uma API REST,
 * como vai acontecer na migração para web), seria preciso alterar todas
 * as telas uma por uma.
 *
 * Com a fábrica, essa decisão de composição fica em um único lugar. As
 * views passam a pedir "ServiceFactory.getPersonagemService()" e não
 * precisam saber qual implementação de repositório está por trás.
 */
public final class ServiceFactory {

    private static final CampanhaService campanhaService =
            new CampanhaService(new CampanhaRepositoryJDBC());
    private static final PersonagemService personagemService =
            new PersonagemService(new PersonagemRepositoryJDBC());
    private static final CriaturaService criaturaService =
            new CriaturaService(new CriaturaRepositoryJDBC());
    private static final ItemService itemService =
            new ItemService(new ItemRepositoryJDBC());
    private static final AtributoService atributoService = new AtributoService();

    private ServiceFactory() {
        // não instanciável
    }

    public static CampanhaService getCampanhaService() { return campanhaService; }
    public static PersonagemService getPersonagemService() { return personagemService; }
    public static CriaturaService getCriaturaService() { return criaturaService; }
    public static ItemService getItemService() { return itemService; }
    public static AtributoService getAtributoService() { return atributoService; }
}
