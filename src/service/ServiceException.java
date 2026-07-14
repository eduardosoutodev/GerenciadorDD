package service;

/**
 * Exceção única usada por toda a camada de serviço, tanto para erros de
 * validação de regra de negócio quanto para falhas de persistência
 * (RepositoryException) propagadas de baixo.
 *
 * Isso simplifica a view: em vez de tratar SQLException, RepositoryException
 * e validações manuais separadamente (como no projeto original), a tela só
 * precisa de um único catch (ServiceException ex).
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
