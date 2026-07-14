package repository;

import model.Criatura;
import java.util.List;

public interface CriaturaRepository {
    List<Criatura> listar() throws RepositoryException;
    void inserir(Criatura criatura) throws RepositoryException;
}
