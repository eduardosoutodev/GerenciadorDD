package repository;

import model.Campanha;
import java.util.List;

/**
 * Interface que define as operações de acesso aos dados de Campanha.
 * A camada de serviço depende desta interface, e não da implementação.
 */
public interface CampanhaRepository {
    List<Campanha> listar() throws RepositoryException;
    void inserir(Campanha campanha) throws RepositoryException;
}
