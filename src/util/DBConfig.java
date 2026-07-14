package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Responsabilidade única: carregar e expor os parâmetros de conexão
 * do arquivo db.properties (localizado na raiz do classpath / src).
 *
 * Se o arquivo não existir ou faltar alguma chave, cai em valores
 * padrão razoáveis para ambiente de desenvolvimento local.
 */
public class DBConfig {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("Aviso: não foi possível carregar db.properties, usando valores padrão. " + e.getMessage());
        }
    }

    public static String getUrl() {
        return props.getProperty("db.url", "jdbc:mysql://localhost:3306/gerenciador_dd3_2?useSSL=false&serverTimezone=UTC");
    }

    public static String getUser() {
        return props.getProperty("db.user", "root");
    }

    public static String getPassword() {
        return props.getProperty("db.password", "root");
    }
}
