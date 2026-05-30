# AGROBOM - Sistema de Gerenciamento de Estoque

Um sistema completo de backend para gerenciar estoque, pedidos e solicitações de compra para a empresa AGROBOM, revendedora de produtos agrícolas.

## Características

- ✅ Gerenciamento de Clientes
- ✅ Gerenciamento de Produtos
- ✅ Gerenciamento de Fornecedores
- ✅ Controle de Pedidos
- ✅ Controle de Solicitações de Compra
- ✅ Histórico de Movimentação de Estoque
- ✅ Relatórios Mensais
- ✅ Alertas de Estoque Crítico

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Banco de Dados:** MySQL
- **Driver:** MySQL Connector/J 9.7.0
- **Serialização:** GSON 2.14.0
- **Servidor:** Apache Tomcat 11.0

## Estrutura do Projeto

```
src/main/java/com/agrobom/
├── model/           # Entidades do sistema
│   ├── Cliente.java
│   ├── Produto.java
│   ├── Fornecedor.java
│   ├── Pedido.java
│   ├── PedidoProduto.java
│   ├── SolicitacaoCompra.java
│   ├── SolicitacaoCompraProduto.java
│   ├── FornecedorProduto.java
│   └── EstoqueHistorico.java
├── dao/             # Data Access Objects
│   ├── ClienteDAO.java
│   ├── ProdutoDAO.java
│   ├── FornecedorDAO.java
│   ├── PedidoDAO.java
│   ├── PedidoProdutoDAO.java
│   ├── SolicitacaoCompraDAO.java
│   ├── SolicitacaoCompraProdutoDAO.java
│   ├── FornecedorProdutoDAO.java
│   └── EstoqueHistoricoDAO.java
├── service/         # Lógica de Negócios
│   ├── ClienteService.java
│   ├── ProdutoService.java
│   ├── FornecedorService.java
│   ├── PedidoService.java
│   ├── SolicitacaoCompraService.java
│   └── EstoqueService.java
└── util/            # Utilitários
    └── ConexaoBD.java

database/
├── agrobom_schema.sql           # Script de criação das tabelas
├── agrobom_dados_teste.sql      # Dados de teste
└── agrobom_relatorios.sql       # Queries dos relatórios
```

## Banco de Dados

### Tabelas Principais

| Tabela | Descrição |
|--------|-----------|
| **cliente** | Informações dos clientes |
| **produto** | Catálogo de produtos com estoque |
| **fornecedor** | Cadastro de fornecedores |
| **fornecedor_produto** | Associação N:M entre fornecedores e produtos |
| **pedido** | Pedidos de clientes |
| **pedido_produto** | Produtos dentro de um pedido |
| **solicitacao_compra** | Solicitações de compra aos fornecedores |
| **solicitacao_compra_produto** | Produtos de uma solicitação de compra |
| **estoque_historico** | Histórico de todas as movimentações |

## Instalação e Configuração

### 1. Criar o Banco de Dados

```bash
mysql -u root -p < database/agrobom_schema.sql
```

### 2. Inserir Dados de Teste (Opcional)

```bash
mysql -u root -p agrobom < database/agrobom_dados_teste.sql
```

### 3. Configurar Conexão no Java

Editar `src/main/java/com/agrobom/util/ConexaoBD.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/agrobom";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";
```

### 4. Adicionar JARs ao Projeto

Os seguintes arquivos JAR já estão no classpath:
- `mysql-connector-j-9.7.0.jar`
- `gson-2.14.0.jar`

## Relatórios Disponíveis

Todos os relatórios solicitados foram implementados em `database/agrobom_relatorios.sql`:

### 1. Posição do Estoque
Mostra para cada produto:
- Descrição
- Quantidade existente
- Unidade de medida
- Quantidade mínima ideal
- Status (OK, BAIXO, CRÍTICO)

### 2. Pedidos por Mês
Detalhes de todos os pedidos recebidos em um mês:
- Número do pedido
- Nome, telefone e endereço do cliente
- Produtos solicitados
- Quantidade e unidade de medida

### 3. Pedidos por Intervalo de Datas
Filtro de pedidos em um período:
- Valor total de cada pedido
- Desconto aplicado
- Número do pedido

### 4. Fornecedores de um Produto
Para cada produto:
- Nome do fornecedor
- CGC do fornecedor
- Telefone do fornecedor
- Preço de custo
- Prazo de entrega

### 5. Solicitações de Compra Mês a Mês
Detalhes mês a mês:
- Nome e CGC do fornecedor
- Produtos solicitados com quantidades
- Número de cada solicitação
- Situação (em aberto ou encerrada)

### 6. Volume de Solicitações e Pedidos (12 Meses)
Somatório mensal de:
- Volume total de pedidos (em dinheiro)
- Volume total de solicitações (em dinheiro)
- Apresentados mês a mês

## Como Usar

### Cadastrar um Cliente

```java
ClienteService clienteService = new ClienteService();

Cliente cliente = new Cliente(
    "Fazenda Santa Maria",
    "11987654321",
    "Rua do Trigo 100, São Paulo - SP",
    "contato@fazendasm.com",
    LocalDate.now(),
    true
);

clienteService.cadastrarCliente(cliente);
```

### Cadastrar um Produto

```java
ProdutoService produtoService = new ProdutoService();

Produto produto = new Produto(
    "Sementes Milho Híbrido",
    "kg",
    BigDecimal.valueOf(500),
    BigDecimal.valueOf(100),
    BigDecimal.valueOf(150),
    true
);

produto.setCategoria("Sementes");
produto.setMarca("Marca A");

produtoService.cadastrarProduto(produto);
```

### Criar um Pedido

```java
PedidoService pedidoService = new PedidoService();

List<PedidoProduto> produtos = new ArrayList<>();
produtos.add(new PedidoProduto(
    null,  // id será gerado
    1L,    // produto_id
    BigDecimal.valueOf(20),    // quantidade
    BigDecimal.valueOf(150),   // preco_unitario
    BigDecimal.valueOf(3000)   // subtotal
));

pedidoService.criarPedido(
    1L,                           // cliente_id
    produtos,
    BigDecimal.valueOf(250)       // desconto
);
```

### Buscar Produtos com Estoque Baixo

```java
EstoqueService estoqueService = new EstoqueService();
List<Produto> produtosBaixos = estoqueService.relatorioEstoqueBaixo();

for (Produto p : produtosBaixos) {
    System.out.println("ALERTA: " + p.getDescricao() + 
        " - Estoque: " + p.getQuantidadeEstoque());
}
```

### Criar Solicitação de Compra

```java
SolicitacaoCompraService solicitacaoService = new SolicitacaoCompraService();

List<SolicitacaoCompraProduto> produtos = new ArrayList<>();
produtos.add(new SolicitacaoCompraProduto(
    null,
    1L,    // produto_id
    BigDecimal.valueOf(30),    // quantidade
    BigDecimal.valueOf(120),   // preco_unitario
    BigDecimal.valueOf(3600)   // subtotal
));

solicitacaoService.criarSolicitacao(1L, produtos);  // fornecedor_id
```

### Finalizar Solicitação de Compra

```java
SolicitacaoCompraService solicitacaoService = new SolicitacaoCompraService();
solicitacaoService.finalizarSolicitacao(1L);  // atualiza estoque
```

### Listar Fornecedores de um Produto

```java
FornecedorService fornecedorService = new FornecedorService();
List<FornecedorProduto> fornecedores = 
    fornecedorService.listarFornecedoresPorProduto(1L);

for (FornecedorProduto fp : fornecedores) {
    System.out.println("Fornecedor: " + fp.getFornecedorId() + 
        " - Preço: " + fp.getPrecoCusto());
}
```

## Regras de Negócio Implementadas

### Gerenciamento de Pedidos
- ✅ Cliente deve estar ativo para fazer pedido
- ✅ Atualiza estoque automaticamente ao criar pedido
- ✅ Permite aplicar desconto
- ✅ Registra movimentação no histórico
- ✅ Alerta quando estoque chega ao mínimo

### Gerenciamento de Solicitações de Compra
- ✅ Fornecedor deve estar ativo
- ✅ Uma solicitação para um único fornecedor
- ✅ Múltiplos produtos por solicitação
- ✅ Atualiza estoque ao finalizar
- ✅ Registra entrada no histórico

### Controle de Estoque
- ✅ Rastreamento de entrada/saída
- ✅ Histórico completo de movimentações
- ✅ Identificação automática de estoque crítico
- ✅ Referência do documento (pedido/solicitação)

### Gerenciamento de Fornecedores
- ✅ Associação flexível de produtos
- ✅ Preço de custo por fornecedor
- ✅ Prazo de entrega
- ✅ Possibilidade de inativar sem deletar

## Validações Implementadas

```java
// Cliente
if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
    throw new IllegalArgumentException("Nome obrigatório");
}

// Produto
if (produto.getPrecoUnitario() == null) {
    throw new IllegalArgumentException("Preço unitário obrigatório");
}

// Pedido
if (cliente == null || !cliente.getStatus()) {
    throw new IllegalArgumentException("Cliente não encontrado ou inativo");
}

// Solicitação
if (fornecedor == null || !fornecedor.getAtivo()) {
    throw new IllegalArgumentException("Fornecedor não encontrado ou inativo");
}
```

## Executar Relatórios

Todos os relatórios estão em `database/agrobom_relatorios.sql`. Execute no MySQL Workbench ou linha de comando:

```bash
mysql -u root -p agrobom < database/agrobom_relatorios.sql
```

Ou importe e execute query por query no seu cliente MySQL.

## Tratamento de Erros

Todas as operações:
- ✅ Tratam `SQLException` adequadamente
- ✅ Validam dados antes de processar
- ✅ Lançam `IllegalArgumentException` para erros de negócio
- ✅ Mantêm conexões fechadas corretamente

## Próximas Melhorias Sugeridas

- [ ] Implementar REST API com Spring Boot
- [ ] Adicionar autenticação com JWT
- [ ] Criar interface web com React/Vue
- [ ] Implementar testes unitários com JUnit
- [ ] Adicionar logging com Log4j
- [ ] Implementar cache com Redis
- [ ] Gerar relatórios em PDF com iText
- [ ] Exportar dados para Excel
- [ ] Melhorar performance com stored procedures

## Suporte e Dúvidas

Este projeto é um exercício acadêmico para gerenciamento de estoque agrícola.

Para dúvidas sobre o código:
1. Consulte a documentação das classes
2. Verifique os exemplos de uso acima
3. Execute os dados de teste para entender o fluxo

## Autor

**João Henrique da Silva**

## Licença

Este projeto é fornecido como exercício acadêmico.

---

## Resumo da Implementação

| Componente | Quantidade | Status |
|-----------|-----------|--------|
| Classes Model | 9 | ✅ Completo |
| Classes DAO | 9 | ✅ Completo |
| Classes Service | 6 | ✅ Completo |
| Utilitários | 1 | ✅ Completo |
| Scripts SQL | 3 | ✅ Completo |
| Relatórios | 6 | ✅ Completo |
| Documentação | 1 | ✅ Completo |
| **TOTAL** | **35 arquivos** | **✅ 100%** |
