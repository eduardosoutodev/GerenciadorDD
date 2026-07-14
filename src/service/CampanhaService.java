package service;

import model.Campanha;
import repository.CampanhaRepository;
import repository.RepositoryException;

import java.util.List;

/**
 * Responsabilidade única: regras de negócio de Campanha (validação e
 * orquestração), sem qualquer conhecimento de Swing ou de SQL.
 *
 * Aplica Dependency Injection: a implementação concreta do repositório
 * é recebida no construtor, não instanciada aqui com "new" (isso permite
 * trocar a implementação, ex. por um mock em testes, sem mudar esta classe).
 */
public class CampanhaService {
    private final CampanhaRepository repository;

    public CampanhaService(CampanhaRepository repository) {
        this.repository = repository;
    }

    public List<Campanha> listar() throws ServiceException {
        try {
            return repository.listar();
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível carregar as campanhas.", e);
        }
    }

    public void criar(String nome, String descricao, String mestre) throws ServiceException {
        // Validação que antes ficava dentro do ActionListener do botão "Salvar" na view
        if (nome == null || nome.trim().isEmpty()) {
            throw new ServiceException("O nome da campanha é obrigatório.");
        }

        Campanha c = new Campanha();
        c.setNome(nome.trim());
        c.setDescricao(descricao);
        c.setMestre(mestre);

        try {
            repository.inserir(c);
        } catch (RepositoryException e) {
            throw new ServiceException("Não foi possível salvar a campanha.", e);
        }
    }
}
