package br.com.agrobombackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.agrobombackend.connection.ConnectionFactory;
import br.com.agrobombackend.model.Fornecedor;

public class FornecedorDAO {

	private Connection connection;

	public FornecedorDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	// SALVAR
	public void salvar(Fornecedor fornecedor) {

	    String sql = "INSERT INTO FORNECEDOR (nome, cnpj, telefone) VALUES (?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

	        stmt.setString(1, fornecedor.getNome());
	        stmt.setString(2, fornecedor.getCnpj());
	        stmt.setString(3, fornecedor.getTelefone());

	        stmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// LISTAR TODOS
	public List<Fornecedor> listarTodos() {

		List<Fornecedor> fornecedores = new ArrayList<>();

		String sql = "SELECT * FROM FORNECEDOR";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setId_fornecedor(rs.getInt("id_fornecedor"));
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setTelefone(rs.getString("telefone"));

				fornecedores.add(fornecedor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fornecedores;
	}

	// BUSCAR POR ID
	public Fornecedor buscarPorId(int id) {

		Fornecedor fornecedor = null;

		String sql = "SELECT * FROM FORNECEDOR WHERE id_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					fornecedor = new Fornecedor();

					fornecedor.setId_fornecedor(rs.getInt("id_fornecedor"));
					fornecedor.setNome(rs.getString("nome"));
					fornecedor.setCnpj(rs.getString("cnpj"));
					fornecedor.setTelefone(rs.getString("telefone"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fornecedor;
	}

	// ATUALIZAR
	public void atualizar(Fornecedor fornecedor) {

		String sql = "UPDATE FORNECEDOR SET nome = ?, cnpj = ?, telefone = ? WHERE id_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, fornecedor.getNome());
			stmt.setString(2, fornecedor.getCnpj());
			stmt.setString(3, fornecedor.getTelefone());
			stmt.setInt(4, fornecedor.getId_fornecedor());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// REMOVER
	public void remover(int id) {

		String sql = "DELETE FROM FORNECEDOR WHERE id_fornecedor = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}