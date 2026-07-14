package repository;

/*
 * Exceção utilizada para representar falhas de persistência.
 * Evita que a camada de serviço e a interface dependam
 * diretamente de SQLException.
 */
public class RepositoryException extends Exception {
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
