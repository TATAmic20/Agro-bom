package br.com.agrobombackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.agrobombackend.connection.ConnectionFactory;
import br.com.agrobombackend.model.Item_pedido;

public class ItemPedidoDAO {

	private Connection connection;

	public ItemPedidoDAO() {
		this.connection = ConnectionFactory.getConnection();
	}

	// SALVAR
	public void salvar(Item_pedido item) {

	    String sql = "INSERT INTO ITEM_PEDIDO (quantidade_pedida, preco_unitario, id_pedido, id_produto) "
	               + "VALUES (?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {

	        stmt.setInt(1, item.getQuantidade_pedida());
	        stmt.setDouble(2, item.getPreco_unitario());
	        stmt.setInt(3, item.getId_pedido());
	        stmt.setInt(4, item.getId_produto());

	        stmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// LISTAR TODOS
	public List<Item_pedido> listarTodos() {

		List<Item_pedido> itens = new ArrayList<>();

		String sql = "SELECT * FROM ITEM_PEDIDO";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Item_pedido item = new Item_pedido();

				item.setId_item_pedido(rs.getInt("id_item_pedido"));
				item.setQuantidade_pedida(rs.getInt("quantidade_pedida"));
				item.setPreco_unitario(rs.getDouble("preco_unitario"));
				item.setId_pedido(rs.getInt("id_pedido"));
				item.setId_produto(rs.getInt("id_produto"));

				itens.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return itens;
	}

	// BUSCAR POR ID
	public Item_pedido buscarPorId(int id) {

		Item_pedido item = null;

		String sql = "SELECT * FROM ITEM_PEDIDO WHERE id_item_pedido = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {

				if (rs.next()) {

					item = new Item_pedido();

					item.setId_item_pedido(rs.getInt("id_item_pedido"));
					item.setQuantidade_pedida(rs.getInt("quantidade_pedida"));
					item.setPreco_unitario(rs.getDouble("preco_unitario"));
					item.setId_pedido(rs.getInt("id_pedido"));
					item.setId_produto(rs.getInt("id_produto"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	// ATUALIZAR
	public void atualizar(Item_pedido item) {

		String sql = "UPDATE ITEM_PEDIDO SET quantidade_pedida = ?, preco_unitario = ?, id_pedido = ?, id_produto = ? WHERE id_item_pedido = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, item.getQuantidade_pedida());
			stmt.setDouble(2, item.getPreco_unitario());
			stmt.setInt(3, item.getId_pedido());
			stmt.setInt(4, item.getId_produto());
			stmt.setInt(5, item.getId_item_pedido());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// REMOVER
	public void remover(int id) {

		String sql = "DELETE FROM ITEM_PEDIDO WHERE id_item_pedido = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, id);

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}