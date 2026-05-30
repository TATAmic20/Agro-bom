package br.com.agrobombackend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.agrobombackend.connection.ConnectionFactory;
import br.com.agrobombackend.model.Produto;

public class ProdutoDAO {

    private Connection connection;

    public ProdutoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // SALVAR
    public void salvar(Produto produto) {

        String sql = "INSERT INTO PRODUTO (nome, descricao, preco, unidade_medida, quantidade_estoque, quantidade_ideal) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getUnidade_medida());
            stmt.setInt(5, produto.getQuantidade_estoque());
            stmt.setInt(6, produto.getQuantidade_ideal());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTAR TODOS
    // CORRIGIDO: campo "nome" estava ausente no mapeamento
    public List<Produto> listarTodos() {

        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT * FROM PRODUTO";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setId_produto(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setUnidade_medida(rs.getString("unidade_medida"));
                produto.setQuantidade_estoque(rs.getInt("quantidade_estoque"));
                produto.setQuantidade_ideal(rs.getInt("quantidade_ideal"));

                produtos.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // BUSCAR POR ID
    // CORRIGIDO: campo "nome" estava ausente no mapeamento
    public Produto buscarPorId(int id) {

        Produto produto = null;

        String sql = "SELECT * FROM PRODUTO WHERE id_produto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    produto = new Produto();

                    produto.setId_produto(rs.getInt("id_produto"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setUnidade_medida(rs.getString("unidade_medida"));
                    produto.setQuantidade_estoque(rs.getInt("quantidade_estoque"));
                    produto.setQuantidade_ideal(rs.getInt("quantidade_ideal"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produto;
    }

    // ATUALIZAR
    public void atualizar(Produto produto) {

        String sql = "UPDATE PRODUTO SET nome = ?, descricao = ?, unidade_medida = ?, quantidade_estoque = ?, quantidade_ideal = ?, preco = ? WHERE id_produto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getUnidade_medida());
            stmt.setInt(4, produto.getQuantidade_estoque());
            stmt.setInt(5, produto.getQuantidade_ideal());
            stmt.setDouble(6, produto.getPreco());
            stmt.setInt(7, produto.getId_produto());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // REMOVER
    public void remover(int id) {

        String sql = "DELETE FROM PRODUTO WHERE id_produto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
