package service;

import repository.jdbc.CampanhaRepositoryJDBC;
import repository.jdbc.CriaturaRepositoryJDBC;
import repository.jdbc.ItemRepositoryJDBC;
import repository.jdbc.PersonagemRepositoryJDBC;

/*
 * Fábrica responsável por criar e fornecer as instâncias dos serviços.
 *
 * Centraliza a criação dos serviços e de seus repositórios, evitando
 * que as telas conheçam ou instanciem as implementações diretamente.
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
