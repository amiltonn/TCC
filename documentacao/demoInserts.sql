INSERT INTO Produto (nome, qtd, custo, preco, unidadeMedidaId)
	VALUES
		('produto1', 1, 1, 1.5, 1),
		('produto2', 2, 2, 2.5, 1),
		('produto3', 3, 3, 3.5, 1),
		('produto4', 4, 4, 4.5, 1);

SELECT * FROM ProdutoView;

SELECT * FROM ProdutoAtual;

UPDATE Produto
	SET nome = nome || ' alterado',
		qtd = qtd + 1,
		custo = custo - 0.5
	WHERE id > 2;
	
SELECT * FROM ProdutoView;

SELECT * FROM ProdutoAtual;

SELECT * FROM Produto;

-- AGORA INSERTS E SELECTS COM CAIXA

INSERT INTO Caixa DEFAULT VALUES;

INSERT INTO CaixaFundo (valor, caixaId)
	VALUES (9000.8, 1);

INSERT INTO CaixaProduto (qtd, produtoId, caixaId)
	VALUES 	(1, 1, 1),
			(2, 2, 1),
			(3, 5, 1),
			(1, 6, 1);

-- UPDATE muito imediato, por isso esta para 5 e 6
UPDATE CaixaProduto
	SET qtd = 110
	WHERE id IN (3, 4);

SELECT * FROM Caixa;

SELECT * FROM CaixaFundo;

SELECT * FROM Estoque;

SELECT * FROM EstoqueProduto;
		
SELECT * FROM CaixaProduto;

SELECT * FROM CaixaProdutoView;

SELECT * FROM CaixaProdutoAtual;

SELECT * FROM CaixaProdutoAberto;

SELECT c.id, c.dataAbertura, c.dataFechamento, cf.valor AS fundo, cf.dataAlteracao AS dataAlteracao, c.estoqueId FROM Caixa AS c
	INNER JOIN CaixaFundo AS cf ON cf.caixaId = c.id;

SELECT c.id, c.dataAbertura, c.dataFechamento, cf.valor AS fundo, cf.dataAlteracao AS dataAlteracao, c.estoqueId FROM Caixa AS c
	INNER JOIN CaixaFundo AS cf ON cf.caixaId = c.id
	WHERE cf.dataAlteracao = (SELECT MAX(dataAlteracao) FROM CaixaFundo WHERE caixaId = c.id);

-- UPDATE Caixa SET dataFechamento = datetime();
