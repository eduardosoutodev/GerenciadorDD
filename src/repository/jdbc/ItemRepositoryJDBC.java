package repository.jdbc;

import model.Item;
import repository.ItemRepository;
import repository.RepositoryException;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryJDBC implements ItemRepository {

    @Override
    public List<Item> listarPorPersonagem(int personagemId) throws RepositoryException {
        List<Item> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens WHERE personagem_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personagemId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item i = new Item();
                    i.setId(rs.getInt("id"));
                    i.setNome(rs.getString("nome"));
                    i.setQuantidade(rs.getInt("quantidade"));
                    i.setPeso(rs.getDouble("peso"));
                    i.setPersonagemId(rs.getInt("personagem_id"));
                    lista.add(i);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar itens do personagem " + personagemId, e);
        }
        return lista;
    }

    @Override
    public void inserir(Item i) throws RepositoryException {
        String sql = "INSERT INTO itens (nome, quantidade, peso, personagem_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, i.getNome());
            stmt.setInt(2, i.getQuantidade());
            stmt.setDouble(3, i.getPeso());
            stmt.setInt(4, i.getPersonagemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir item", e);
        }
    }

    @Override
    public void excluir(int id) throws RepositoryException {
        String sql = "DELETE FROM itens WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao excluir item " + id, e);
        }
    }
}
