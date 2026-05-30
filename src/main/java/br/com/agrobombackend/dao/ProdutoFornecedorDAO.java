package br.com.agrobombackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.agrobombackend.connection.ConnectionFactory;
import br.com.agrobombackend.model.Produto_fornecedor;

public class ProdutoFornecedorDAO {

	private Connection connection;

	public ProdutoFornecedorDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	// SALVAR
	public void salvar(Produto_fornecedor pf) {

		String sql = "INSERT INTO PRODUTO_FORNECEDOR (id_produto, id_fornecedor) VALUES (?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, pf.getId_produto());
			stmt.setInt(2, pf.getId_fornecedor());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LISTAR TODOS
	public List<Produto_fornecedor> listarTodos() {

		List<Produto_fornecedor> lista = new ArrayList<>();

		String sql = "SELECT * FROM PRODUTO_FORNECEDOR";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Produto_fornecedor pf = new Produto_fornecedor();

				pf.setId_produto_fornecedor(rs.getInt("id_produto_fornecedor"));
				pf.setId_produto(rs.getInt("id_produto"));
				pf.setId_fornecedor(rs.getInt("id_fornecedor"));

				lista.add(pf);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// BUSCAR POR ID
	public Produto_fornecedor buscarPorId(int id) {

		Produto_fornecedor pf = null;

		String sql = "SELECT * FROM PRODUTO_FORNECEDOR WHERE id_produto_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					pf = new Produto_fornecedor();

					pf.setId_produto_fornecedor(rs.getInt("id_produto_fornecedor"));
					pf.setId_produto(rs.getInt("id_produto"));
					pf.setId_fornecedor(rs.getInt("id_fornecedor"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pf;
	}

	// ATUALIZAR
	public void atualizar(Produto_fornecedor pf) {

		String sql = "UPDATE PRODUTO_FORNECEDOR SET id_produto = ?, id_fornecedor = ? WHERE id_produto_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, pf.getId_produto());
			stmt.setInt(2, pf.getId_fornecedor());
			stmt.setInt(3, pf.getId_produto_fornecedor());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// REMOVER
	public void remover(int id) {

		String sql = "DELETE FROM PRODUTO_FORNECEDOR WHERE id_produto_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}