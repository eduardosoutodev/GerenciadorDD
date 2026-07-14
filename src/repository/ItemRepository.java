package repository;

import model.Item;
import java.util.List;

public interface ItemRepository {
    List<Item> listarPorPersonagem(int personagemId) throws RepositoryException;
    void inserir(Item item) throws RepositoryException;
    void excluir(int id) throws RepositoryException;
}
