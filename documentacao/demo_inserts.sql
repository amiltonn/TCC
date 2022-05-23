INSERT INTO item (nome, qtd, custo, preco, unidade_medida_id)
	VALUES
		('item1', 1, 1, 1.5, 1),
		('item2', 2, 2, 2.5, 1),
		('item3', 3, 3, 3.5, 1),
		('item4', 4, 4, 4.5, 1);

SELECT * FROM item_view;

SELECT * FROM item_atual;

UPDATE item
	SET nome = nome || ' alterado',
		qtd = qtd + 1,
		custo = custo - 0.5
	WHERE id > 2;
	
SELECT * FROM item_view;

SELECT * FROM item_atual;

SELECT * FROM item;

-- AGORA INSERTS E SELECTS COM CAIXA

INSERT INTO caixa DEFAULT VALUES;

INSERT INTO caixa_fundo (valor, caixa_id)
	VALUES (9000.8, 1);

INSERT INTO caixa_item (qtd, item_id, caixa_id)
	VALUES 	(1, 1, 1),
			(2, 2, 1),
			(3, 5, 1),
			(1, 6, 1);

-- UPDATE muito imediato, por isso esta para 5 e 6
UPDATE caixa_item
	SET qtd = 110
	WHERE id IN (3, 4);

SELECT * FROM caixa;

SELECT * FROM caixa_fundo;

SELECT * FROM estoque;

SELECT * FROM estoque_item;
		
SELECT * FROM caixa_item;

SELECT * FROM caixa_item_view;

SELECT * FROM caixa_item_atual;

SELECT * FROM caixa_item_aberto;

SELECT c.id, c.data_abertura, c.data_fechamento, cf.valor AS fundo, cf.data_alteracao AS data_alteracao, c.estoque_id FROM caixa AS c
	INNER JOIN caixa_fundo AS cf ON cf.caixa_id = c.id;

SELECT c.id, c.data_abertura, c.data_fechamento, cf.valor AS fundo, cf.data_alteracao AS data_alteracao, c.estoque_id FROM caixa AS c
	INNER JOIN caixa_fundo AS cf ON cf.caixa_id = c.id
	WHERE cf.data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_fundo WHERE caixa_id = c.id);

-- UPDATE caixa SET data_fechamento = datetime();