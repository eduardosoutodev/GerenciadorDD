package repository;

/**
 * Exceção de negócio que encapsula falhas de persistência.
 *
 * Code smell corrigido: antes, cada tela (view) tratava SQLException
 * diretamente, o que acoplava a interface gráfica a detalhes de JDBC.
 * Com essa exceção "traduzida", a camada de serviço e a view só
 * conhecem RepositoryException, e o Swing não precisa mais importar
 * java.sql.SQLException.
 */
public class RepositoryException extends Exception {
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
