package repository.jdbc;

import model.Personagem;
import repository.PersonagemRepository;
import repository.RepositoryException;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PersonagemRepositoryJDBC implements PersonagemRepository {

    @Override
    public void inserir(Personagem p) throws RepositoryException {
        String sql = "INSERT INTO personagens (nome, classe, nivel, raca, alinhamento, forca, destreza, "
                + "constituicao, inteligencia, sabedoria, carisma, pv_max, pv_atual, ca, notas, campanha_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherParametros(stmt, p);
            if (p.getCampanhaId() > 0) {
                stmt.setInt(16, p.getCampanhaId());
            } else {
                stmt.setNull(16, Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir personagem", e);
        }
    }

    @Override
    public List<Personagem> listar() throws RepositoryException {
        List<Personagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM personagens";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao listar personagens", e);
        }
        return lista;
    }

    @Override
    public Personagem buscarPorId(int id) throws RepositoryException {
        String sql = "SELECT * FROM personagens WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao buscar personagem " + id, e);
        }
        return null;
    }

    @Override
    public void atualizar(Personagem p) throws RepositoryException {
        String sql = "UPDATE personagens SET nome=?, classe=?, nivel=?, raca=?, alinhamento=?, forca=?, "
                + "destreza=?, constituicao=?, inteligencia=?, sabedoria=?, carisma=?, pv_max=?, pv_atual=?, "
                + "ca=?, notas=?, campanha_id=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            preencherParametros(stmt, p);
            if (p.getCampanhaId() > 0) {
                stmt.setInt(16, p.getCampanhaId());
            } else {
                stmt.setNull(16, Types.INTEGER);
            }
            stmt.setInt(17, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao atualizar personagem " + p.getId(), e);
        }
    }

    @Override
    public void excluir(int id) throws RepositoryException {
        String sql = "DELETE FROM personagens WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao excluir personagem " + id, e);
        }
    }

    // Extraído para eliminar duplicação entre inserir() e atualizar() (code smell: duplicated code)
    private void preencherParametros(PreparedStatement stmt, Personagem p) throws SQLException {
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getClasse());
        stmt.setInt(3, p.getNivel());
        stmt.setString(4, p.getRaca());
        stmt.setString(5, p.getAlinhamento());
        stmt.setInt(6, p.getForca());
        stmt.setInt(7, p.getDestreza());
        stmt.setInt(8, p.getConstituicao());
        stmt.setInt(9, p.getInteligencia());
        stmt.setInt(10, p.getSabedoria());
        stmt.setInt(11, p.getCarisma());
        stmt.setInt(12, p.getPvMax());
        stmt.setInt(13, p.getPvAtual());
        stmt.setInt(14, p.getCa());
        stmt.setString(15, p.getNotas());
    }

    private Personagem mapear(ResultSet rs) throws SQLException {
        Personagem p = new Personagem();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setClasse(rs.getString("classe"));
        p.setNivel(rs.getInt("nivel"));
        p.setRaca(rs.getString("raca"));
        p.setAlinhamento(rs.getString("alinhamento"));
        p.setForca(rs.getInt("forca"));
        p.setDestreza(rs.getInt("destreza"));
        p.setConstituicao(rs.getInt("constituicao"));
        p.setInteligencia(rs.getInt("inteligencia"));
        p.setSabedoria(rs.getInt("sabedoria"));
        p.setCarisma(rs.getInt("carisma"));
        p.setPvMax(rs.getInt("pv_max"));
        p.setPvAtual(rs.getInt("pv_atual"));
        p.setCa(rs.getInt("ca"));
        p.setNotas(rs.getString("notas"));
        p.setCampanhaId(rs.getInt("campanha_id"));
        return p;
    }
}
