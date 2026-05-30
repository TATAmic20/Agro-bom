-- ============================================================
-- Script de Inserção de Dados de Teste - AGROBOM
-- ============================================================

USE agrobom;

-- ============================================================
-- Dados de Teste: CLIENTE
-- ============================================================
INSERT INTO cliente (nome, telefone, endereco, email, data_cadastro, status) VALUES
('Fazenda Santa Maria', '11987654321', 'Rua do Trigo 100, São Paulo - SP', 'contato@fazendasm.com', '2026-01-15', true),
('Agricultura União Ltda', '1134567890', 'Av. Agrícola 250, Campinas - SP', 'vendas@agruniao.com', '2026-02-10', true),
('Cooperativa Regional de Produtores', '1925554444', 'Estrada Rural KM 10, Limeira - SP', 'coop@regionalcoop.com', '2026-03-20', true),
('Produtor Rural João Silva', '11999888777', 'Sítio das Flores 50, Jundiaí - SP', 'joao@sitoruralflores.com', '2026-04-05', true);

-- ============================================================
-- Dados de Teste: FORNECEDOR
-- ============================================================
INSERT INTO fornecedor (nome, cgc, telefone, email, endereco, ativo, data_cadastro) VALUES
('Sementes Brasil S/A', '12345678000190', '1138888888', 'vendas@sementesbr.com', 'Rua das Flores 100, São Paulo - SP', true, '2026-01-10'),
('Adubos Premium Ltda', '98765432000180', '1133333333', 'contato@adubospremium.com', 'Av. Industrial 500, Guarulhos - SP', true, '2026-01-15'),
('Defensivos Agrícolas Nacional', '55555555000170', '1144444444', 'vendas@defensivosnal.com', 'Rua Comercial 300, São Bernardo - SP', true, '2026-02-01'),
('Equipamentos Agrícolas Pro', '11111111000160', '1155555555', 'suporte@equipagropro.com', 'Av. Tecnológica 700, Campinas - SP', true, '2026-02-10');

-- ============================================================
-- Dados de Teste: PRODUTO
-- ============================================================
INSERT INTO produto (descricao, unidade_medida, quantidade_estoque, quantidade_minima, preco_unitario, ativo, categoria, marca) VALUES
('Sementes Milho Híbrido XYZ', 'kg', 500.00, 100.00, 150.00, true, 'Sementes', 'Marca A'),
('Adubo NPK 20-20-20', 'kg', 1000.00, 200.00, 25.00, true, 'Adubos', 'Premium'),
('Inseticida Natural 1L', 'litro', 300.00, 50.00, 120.00, true, 'Defensivos', 'Nacional'),
('Fungicida Sulfato 500ml', 'ml', 200.00, 30.00, 85.00, true, 'Defensivos', 'Nacional'),
('Sementes Soja RR', 'kg', 400.00, 80.00, 180.00, true, 'Sementes', 'Marca B'),
('Calcário Agrícola 50kg', 'saca', 150.00, 20.00, 45.00, true, 'Corretivos', 'Brand C');

-- ============================================================
-- Dados de Teste: FORNECEDOR_PRODUTO
-- ============================================================
INSERT INTO fornecedor_produto (fornecedor_id, produto_id, preco_custo, prazo_entrega) VALUES
(1, 1, 120.00, '5 dias'),
(1, 5, 150.00, '5 dias'),
(2, 2, 20.00, '3 dias'),
(2, 6, 35.00, '2 dias'),
(3, 3, 100.00, '7 dias'),
(3, 4, 70.00, '7 dias'),
(4, 2, 22.00, '4 dias');

-- ============================================================
-- Dados de Teste: PEDIDO
-- ============================================================
INSERT INTO pedido (cliente_id, data_pedido, valor_total, desconto, status) VALUES
(1, '2026-05-15 10:30:00', 5500.00, 250.00, 'finalizado'),
(2, '2026-05-16 14:45:00', 3200.00, 100.00, 'finalizado'),
(3, '2026-05-17 09:15:00', 8500.00, 500.00, 'finalizado'),
(1, '2026-05-20 11:00:00', 2100.00, 0.00, 'aberto'),
(4, '2026-05-21 16:30:00', 4500.00, 200.00, 'finalizado');

-- ============================================================
-- Dados de Teste: PEDIDO_PRODUTO
-- ============================================================
INSERT INTO pedido_produto (pedido_id, produto_id, quantidade, preco_unitario, subtotal) VALUES
(1, 1, 20, 150.00, 3000.00),
(1, 2, 50, 25.00, 1250.00),
(1, 3, 10, 120.00, 1200.00),
(2, 2, 80, 25.00, 2000.00),
(2, 6, 40, 45.00, 1800.00),
(3, 1, 30, 150.00, 4500.00),
(3, 4, 20, 85.00, 1700.00),
(3, 5, 15, 180.00, 2700.00),
(4, 3, 15, 120.00, 1800.00),
(4, 4, 5, 85.00, 425.00),
(5, 5, 18, 180.00, 3240.00),
(5, 2, 30, 25.00, 750.00);

-- ============================================================
-- Dados de Teste: SOLICITACAO_COMPRA
-- ============================================================
INSERT INTO solicitacao_compra (fornecedor_id, data_solicitacao, status, valor_total) VALUES
(1, '2026-05-10 08:00:00', 'encerrada', 4500.00),
(2, '2026-05-12 10:30:00', 'encerrada', 2200.00),
(3, '2026-05-18 14:00:00', 'em aberto', 3500.00),
(1, '2026-05-19 09:15:00', 'em aberto', 5400.00);

-- ============================================================
-- Dados de Teste: SOLICITACAO_COMPRA_PRODUTO
-- ============================================================
INSERT INTO solicitacao_compra_produto (solicitacao_id, produto_id, quantidade, preco_unitario, subtotal) VALUES
(1, 1, 30, 120.00, 3600.00),
(1, 5, 25, 150.00, 3750.00),
(2, 2, 100, 20.00, 2000.00),
(2, 6, 50, 35.00, 1750.00),
(3, 3, 25, 100.00, 2500.00),
(3, 4, 30, 70.00, 2100.00),
(4, 1, 40, 120.00, 4800.00),
(4, 5, 20, 150.00, 3000.00);

-- ============================================================
-- Dados de Teste: ESTOQUE_HISTORICO
-- ============================================================
INSERT INTO estoque_historico (produto_id, data_movimentacao, quantidade_antes, quantidade_depois, tipo, referencia) VALUES
(1, '2026-05-15 10:30:00', 520.00, 500.00, 'saida', 'Pedido #1'),
(2, '2026-05-15 10:30:00', 1050.00, 1000.00, 'saida', 'Pedido #1'),
(3, '2026-05-15 10:30:00', 310.00, 300.00, 'saida', 'Pedido #1'),
(1, '2026-05-10 08:00:00', 490.00, 520.00, 'entrada', 'Solicitação Compra #1'),
(2, '2026-05-12 10:30:00', 900.00, 1000.00, 'entrada', 'Solicitação Compra #2');

-- ============================================================
-- Verificação de dados inseridos
-- ============================================================
SELECT 'Total de Clientes:' as info, COUNT(*) as quantidade FROM cliente;
SELECT 'Total de Fornecedores:' as info, COUNT(*) as quantidade FROM fornecedor;
SELECT 'Total de Produtos:' as info, COUNT(*) as quantidade FROM produto;
SELECT 'Total de Pedidos:' as info, COUNT(*) as quantidade FROM pedido;
SELECT 'Total de Solicitações de Compra:' as info, COUNT(*) as quantidade FROM solicitacao_compra;
