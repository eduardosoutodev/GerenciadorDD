package service;

import model.Item;
import repository.ItemRepository;
import repository.RepositoryException;

import java.util.List;

/**
 * Responsabilidade única: regras de negócio de Item de inventário.
 *
 * A criação de item com quantidade=1 e peso=1.0 "chutados" estava dentro
 * do ActionListener do botão "Adicionar Item" em PanelCharacterSheet.
 * Isso foi extraído para cá como uma regra de negócio nomeada e testável.
 */
public class ItemService {
    private static final int QUANTIDADE_PADRAO = 1;
    private static final double PESO_PADRAO = 1.0;

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> listarPorPersonagem(int personagemId) throws ServiceException {
        try {
            return repository.listarPorPersonagem(personagemId);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível carregar o inventário.", e);
        }
    }

    public void adicionarItemPadrao(String nome, int personagemId) throws ServiceException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ServiceException("O nome do item é obrigatório.");
        }
        Item item = new Item();
        item.setNome(nome.trim());
        item.setQuantidade(QUANTIDADE_PADRAO);
        item.setPeso(PESO_PADRAO);
        item.setPersonagemId(personagemId);
        try {
            repository.inserir(item);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível adicionar o item.", e);
        }
    }

    public void excluir(int id) throws ServiceException {
        try {
            repository.excluir(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível remover o item.", e);
        }
    }
}
