package br.com.agrobombackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.agrobombackend.connection.ConnectionFactory;
import br.com.agrobombackend.model.SolicitacaoCompra;

public class SolicitacaoCompraDAO {

	private Connection connection;

	public SolicitacaoCompraDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	// SALVAR
	public void salvar(SolicitacaoCompra solicitacao) {

		String sql = "INSERT INTO SOLICITACAO_COMPRA (numero_solicitacao, data_solicitacao, situacao, valor_total, id_fornecedor) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, solicitacao.getNumero_solicitacao());
			stmt.setDate(2, new java.sql.Date(solicitacao.getData_solicitacao().getTime()));
			stmt.setString(3, solicitacao.getSituacao());
			stmt.setDouble(4, solicitacao.getValor_total());
			stmt.setInt(5, solicitacao.getId_fornecedor());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LISTAR TODOS
	public List<SolicitacaoCompra> listarTodos() {

		List<SolicitacaoCompra> lista = new ArrayList<>();

		String sql = "SELECT * FROM SOLICITACAO_COMPRA";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				SolicitacaoCompra s = new SolicitacaoCompra();

				s.setId_solicitacao(rs.getInt("id_solicitacao"));
				s.setNumero_solicitacao(rs.getInt("numero_solicitacao"));
				s.setData_solicitacao(rs.getDate("data_solicitacao"));
				s.setSituacao(rs.getString("situacao"));
				s.setValor_total(rs.getDouble("valor_total"));
				s.setId_fornecedor(rs.getInt("id_fornecedor"));

				lista.add(s);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// BUSCAR POR ID
	public SolicitacaoCompra buscarPorId(int id) {

		SolicitacaoCompra s = null;

		String sql = "SELECT * FROM SOLICITACAO_COMPRA WHERE id_solicitacao = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					s = new SolicitacaoCompra();

					s.setId_solicitacao(rs.getInt("id_solicitacao"));
					s.setNumero_solicitacao(rs.getInt("numero_solicitacao"));
					s.setData_solicitacao(rs.getDate("data_solicitacao"));
					s.setSituacao(rs.getString("situacao"));
					s.setValor_total(rs.getDouble("valor_total"));
					s.setId_fornecedor(rs.getInt("id_fornecedor"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	// ATUALIZAR
	public void atualizar(SolicitacaoCompra solicitacao) {

		String sql = "UPDATE SOLICITACAO_COMPRA SET numero_solicitacao = ?, data_solicitacao = ?, situacao = ?, valor_total = ?, id_fornecedor = ? WHERE id_solicitacao = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, solicitacao.getNumero_solicitacao());
			stmt.setDate(2, new java.sql.Date(solicitacao.getData_solicitacao().getTime()));
			stmt.setString(3, solicitacao.getSituacao());
			stmt.setDouble(4, solicitacao.getValor_total());
			stmt.setInt(5, solicitacao.getId_fornecedor());
			stmt.setInt(6, solicitacao.getId_solicitacao());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// REMOVER
	public void remover(int id) {

		String sql = "DELETE FROM SOLICITACAO_COMPRA WHERE id_solicitacao = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}