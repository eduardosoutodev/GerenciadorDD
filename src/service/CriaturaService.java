package service;

import model.Criatura;
import repository.CriaturaRepository;
import repository.RepositoryException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsabilidade única: regras de negócio do Bestiário, incluindo a
 * lógica de filtro por texto (antes embutida em PanelBestiary.filtrarCriaturas(),
 * misturada com atualização de componentes Swing).
 */
public class CriaturaService {
    private final CriaturaRepository repository;

    public CriaturaService(CriaturaRepository repository) {
        this.repository = repository;
    }

    public List<Criatura> listar() throws ServiceException {
        try {
            return repository.listar();
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível carregar as criaturas.", e);
        }
    }

    public List<Criatura> filtrar(List<Criatura> todas, String termoBusca) {
        if (termoBusca == null || termoBusca.trim().isEmpty()) {
            return todas;
        }
        String termo = termoBusca.toLowerCase().trim();
        return todas.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termo) || c.getTipo().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }
}
