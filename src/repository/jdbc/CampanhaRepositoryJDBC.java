package repository.jdbc;

import model.Campanha;
import repository.CampanhaRepository;
import repository.RepositoryException;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação concreta de CampanhaRepository usando JDBC puro.
 * Responsabilidade única: converter linhas do banco em objetos Campanha
 * e vice-versa. Não conhece Swing nem regras de negócio.
 */
public class CampanhaRepositoryJDBC implements CampanhaRepository {

    @Override
    public List<Campanha> listar() throws RepositoryException {
        List<Campanha> lista = new ArrayList<>();
        String sql = "SELECT * FROM campanhas";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar campanhas", e);
        }
        return lista;
    }

    @Override
    public void inserir(Campanha campanha) throws RepositoryException {
        String sql = "INSERT INTO campanhas (nome, descricao, mestre) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, campanha.getNome());
            stmt.setString(2, campanha.getDescricao());
            stmt.setString(3, campanha.getMestre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir campanha", e);
        }
    }

    private Campanha mapear(ResultSet rs) throws SQLException {
        Campanha c = new Campanha();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setDescricao(rs.getString("descricao"));
        c.setMestre(rs.getString("mestre"));
        return c;
    }
}
