package repository;

import model.Personagem;
import java.util.List;

public interface PersonagemRepository {
    List<Personagem> listar() throws RepositoryException;
    Personagem buscarPorId(int id) throws RepositoryException;
    void inserir(Personagem personagem) throws RepositoryException;
    void atualizar(Personagem personagem) throws RepositoryException;
    void excluir(int id) throws RepositoryException;
}
