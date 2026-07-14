package repository;

import model.Campanha;
import java.util.List;

/**
 * Contrato de acesso a dados de Campanha.
 * A camada de serviço depende desta abstração, não da implementação
 * concreta (JDBC). Isso aplica o Dependency Inversion Principle (DIP):
 * módulos de alto nível (services) não dependem de módulos de baixo
 * nível (acesso a banco), ambos dependem desta interface.
 */
public interface CampanhaRepository {
    List<Campanha> listar() throws RepositoryException;
    void inserir(Campanha campanha) throws RepositoryException;
}
