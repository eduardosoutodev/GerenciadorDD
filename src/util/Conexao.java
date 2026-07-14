package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsabilidade única: abrir uma conexão JDBC.
 *
 * Code smell corrigido: no projeto original, URL/usuário/senha do banco
 * estavam fixos ("hardcoded") diretamente no código-fonte. Agora esses
 * valores vêm de db.properties (ver util.DBConfig), então trocar de
 * ambiente (ex: banco de testes) não exige recompilar nada.
 */
public class Conexao {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado", e);
        }
    }
}
