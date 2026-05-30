package br.com.agrobombackend.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import br.com.agrobombackend.dao.ClienteDAO;
import br.com.agrobombackend.model.Cliente;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ClienteDAO dao;

	@Override
	public void init() {
		dao = new ClienteDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao == null) {
			request.setAttribute("clientes", dao.listarTodos());
			request.getRequestDispatcher("/paginas/clientes.jsp").forward(request, response);
			return;
		}

		if ("editar".equals(acao)) {
			String cpf = request.getParameter("cpf");

			Cliente cliente = dao.buscarPorCpf(cpf);

			request.setAttribute("cliente", cliente);
			request.setAttribute("clientes", dao.listarTodos());

			request.getRequestDispatcher("/paginas/clientes.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("UTF-8");

		String acao = request.getParameter("acao");

		if (acao == null || acao.equals("salvar")) {

			Cliente cliente = new Cliente();
			cliente.setCpf_cliente(request.getParameter("cpf"));
			cliente.setNome_cliente(request.getParameter("nome"));
			cliente.setTelefone_cliente(request.getParameter("telefone"));
			cliente.setEndereco_cliente(request.getParameter("endereco"));

			Cliente existente = dao.buscarPorCpf(String.valueOf(cliente.getCpf_cliente()));

			if (existente == null) {
				dao.salvar(cliente);
			} else {
				dao.atualizar(cliente);
			}

			response.sendRedirect("clientes");
			return;
		}

		if ("remover".equals(acao)) {

			String cpf = request.getParameter("cpf");
			dao.remover(cpf);

			response.sendRedirect("clientes");
		}
	}
}