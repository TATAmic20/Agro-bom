-- ============================================================
-- Script de Criação do Banco de Dados AGROBOM
-- ============================================================

-- Criar Banco de Dados
CREATE DATABASE IF NOT EXISTS agrobom;
USE agrobom;

-- ============================================================
-- Tabela CLIENTE
-- ============================================================
CREATE TABLE cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    email VARCHAR(150),
    data_cadastro DATE NOT NULL,
    status BOOLEAN DEFAULT true,
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Cliente
CREATE INDEX idx_cliente_status ON cliente(status);
CREATE INDEX idx_cliente_nome ON cliente(nome);

-- ============================================================
-- Tabela FORNECEDOR
-- ============================================================
CREATE TABLE fornecedor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    cgc VARCHAR(20) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(150),
    endereco VARCHAR(255),
    ativo BOOLEAN DEFAULT true,
    data_cadastro DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Fornecedor
CREATE INDEX idx_fornecedor_ativo ON fornecedor(ativo);
CREATE INDEX idx_fornecedor_cgc ON fornecedor(cgc);

-- ============================================================
-- Tabela PRODUTO
-- ============================================================
CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(255) NOT NULL,
    unidade_medida VARCHAR(20) NOT NULL,
    quantidade_estoque DECIMAL(12, 2) DEFAULT 0,
    quantidade_minima DECIMAL(12, 2) DEFAULT 0,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    ativo BOOLEAN DEFAULT true,
    categoria VARCHAR(100),
    marca VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Produto
CREATE INDEX idx_produto_ativo ON produto(ativo);
CREATE INDEX idx_produto_estoque_critico ON produto(quantidade_estoque, quantidade_minima);

-- ============================================================
-- Tabela FORNECEDOR_PRODUTO (Muitos-para-muitos)
-- ============================================================
CREATE TABLE fornecedor_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fornecedor_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    preco_custo DECIMAL(10, 2),
    prazo_entrega VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE,
    UNIQUE KEY unique_fornecedor_produto (fornecedor_id, produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Fornecedor_Produto
CREATE INDEX idx_fornecedor_produto_fornecedor ON fornecedor_produto(fornecedor_id);
CREATE INDEX idx_fornecedor_produto_produto ON fornecedor_produto(produto_id);

-- ============================================================
-- Tabela PEDIDO
-- ============================================================
CREATE TABLE pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    data_pedido DATETIME NOT NULL,
    valor_total DECIMAL(12, 2),
    desconto DECIMAL(10, 2) DEFAULT 0,
    status VARCHAR(50) DEFAULT 'aberto',
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Pedido
CREATE INDEX idx_pedido_cliente ON pedido(cliente_id);
CREATE INDEX idx_pedido_data ON pedido(data_pedido);
CREATE INDEX idx_pedido_status ON pedido(status);

-- ============================================================
-- Tabela PEDIDO_PRODUTO
-- ============================================================
CREATE TABLE pedido_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade DECIMAL(12, 2) NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Pedido_Produto
CREATE INDEX idx_pedido_produto_pedido ON pedido_produto(pedido_id);
CREATE INDEX idx_pedido_produto_produto ON pedido_produto(produto_id);

-- ============================================================
-- Tabela SOLICITACAO_COMPRA
-- ============================================================
CREATE TABLE solicitacao_compra (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fornecedor_id BIGINT NOT NULL,
    data_solicitacao DATETIME NOT NULL,
    status VARCHAR(50) DEFAULT 'em aberto',
    valor_total DECIMAL(12, 2),
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Solicitacao_Compra
CREATE INDEX idx_solicitacao_compra_fornecedor ON solicitacao_compra(fornecedor_id);
CREATE INDEX idx_solicitacao_compra_data ON solicitacao_compra(data_solicitacao);
CREATE INDEX idx_solicitacao_compra_status ON solicitacao_compra(status);

-- ============================================================
-- Tabela SOLICITACAO_COMPRA_PRODUTO
-- ============================================================
CREATE TABLE solicitacao_compra_produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    solicitacao_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade DECIMAL(12, 2) NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(12, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (solicitacao_id) REFERENCES solicitacao_compra(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Solicitacao_Compra_Produto
CREATE INDEX idx_solicitacao_compra_produto_solicitacao ON solicitacao_compra_produto(solicitacao_id);
CREATE INDEX idx_solicitacao_compra_produto_produto ON solicitacao_compra_produto(produto_id);

-- ============================================================
-- Tabela ESTOQUE_HISTORICO
-- ============================================================
CREATE TABLE estoque_historico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto_id BIGINT NOT NULL,
    data_movimentacao DATETIME NOT NULL,
    quantidade_antes DECIMAL(12, 2),
    quantidade_depois DECIMAL(12, 2),
    tipo VARCHAR(50),
    referencia VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para Estoque_Historico
CREATE INDEX idx_estoque_historico_produto ON estoque_historico(produto_id);
CREATE INDEX idx_estoque_historico_data ON estoque_historico(data_movimentacao);
CREATE INDEX idx_estoque_historico_tipo ON estoque_historico(tipo);
