package repository.jdbc;

import model.Criatura;
import repository.CriaturaRepository;
import repository.RepositoryException;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CriaturaRepositoryJDBC implements CriaturaRepository {

    @Override
    public List<Criatura> listar() throws RepositoryException {
        List<Criatura> lista = new ArrayList<>();
        String sql = "SELECT * FROM criaturas";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Criatura c = new Criatura();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo(rs.getString("tipo"));
                c.setNd(rs.getString("nd"));
                c.setTamanho(rs.getString("tamanho"));
                c.setCa(rs.getInt("ca"));
                c.setPv(rs.getString("pv"));
                c.setForca(rs.getInt("forca"));
                c.setDestreza(rs.getInt("destreza"));
                c.setConstituicao(rs.getInt("constituicao"));
                c.setInteligencia(rs.getInt("inteligencia"));
                c.setSabedoria(rs.getInt("sabedoria"));
                c.setCarisma(rs.getInt("carisma"));
                c.setHabilidades(rs.getString("habilidades"));
                c.setAcoes(rs.getString("acoes"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar criaturas", e);
        }
        return lista;
    }

    @Override
    public void inserir(Criatura c) throws RepositoryException {
        String sql = "INSERT INTO criaturas (nome, tipo, nd, tamanho, ca, pv, forca, destreza, constituicao, "
                + "inteligencia, sabedoria, carisma, habilidades, acoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());
            stmt.setString(3, c.getNd());
            stmt.setString(4, c.getTamanho());
            stmt.setInt(5, c.getCa());
            stmt.setString(6, c.getPv());
            stmt.setInt(7, c.getForca());
            stmt.setInt(8, c.getDestreza());
            stmt.setInt(9, c.getConstituicao());
            stmt.setInt(10, c.getInteligencia());
            stmt.setInt(11, c.getSabedoria());
            stmt.setInt(12, c.getCarisma());
            stmt.setString(13, c.getHabilidades());
            stmt.setString(14, c.getAcoes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir criatura", e);
        }
    }
}
