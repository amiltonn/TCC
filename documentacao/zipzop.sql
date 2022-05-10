-- DROP EM TUDO


DROP VIEW IF EXISTS caixa_item_aberto;

DROP VIEW IF EXISTS item_atual;

DROP VIEW IF EXISTS caixa_item_view;

DROP VIEW IF EXISTS item_view;

DROP TABLE IF EXISTS estoque_item;

DROP TABLE IF EXISTS estoque;

DROP TABLE IF EXISTS venda_item;

DROP TABLE IF EXISTS caixa_item;

DROP TABLE IF EXISTS venda;

DROP TABLE IF EXISTS caixa;

DROP TABLE IF EXISTS forma_pagamento;

DROP TABLE IF EXISTS receita_item;

DROP TABLE IF EXISTS insumo;

DROP TABLE IF EXISTS unidade_medida;

DROP TABLE IF EXISTS status_item;

DROP TABLE IF EXISTS item;

-- CREATES

CREATE TABLE IF NOT EXISTS status_item(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS unidade_medida(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS forma_pagamento(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estoque(
	id INTEGER PRIMARY KEY NOT NULL,
	data_alteracao DATETIME NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS item(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	qtd INTEGER NOT NULL,
	custo NUMERIC NOT NULL,
	preco NUMERIC,
	data_alteracao DATETIME NOT NULL,
	item_antes_id INTEGER DEFAULT NULL,
	status_item_id INTEGER NOT NULL,
	unidade_medida_id INTEGER NOT NULL,
	FOREIGN KEY (status_item_id) REFERENCES status_item (id) ON UPDATE CASCADE,
	FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS estoque_item(
	estoque_id INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	UNIQUE(estoque_id, item_id),
	FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE CASCADE,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS caixa(
	id INTEGER PRIMARY KEY NOT NULL,
	fundo NUMERIC NOT NULL,
	data_abertura DATETIME NOT NULL,
	data_fechamento DATETIME,
	estoque_id INTEGER NOT NULL,
	FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS caixa_item(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	caixa_id INTEGER NOT NULL,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE CASCADE,
	FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS venda(
	id INTEGER PRIMARY KEY NOT NULL,
	valor_pago NUMERIC NOT NULL,
	valor_venda NUMERIC NOT NULL,
	data_pagamento DATETIME NOT NULL,
	forma_pagamento_id INTEGER NOT NULL,
	caixa_id INTEGER NOT NULL,
	FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id) ON UPDATE CASCADE,
	FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS venda_item(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	preco_venda NUMERIC,
	venda_id INTEGER NOT NULL,
	caixa_item_id INTEGER NOT NULL,
	FOREIGN KEY (venda_id) REFERENCES venda (id) ON UPDATE CASCADE,
	FOREIGN KEY (caixa_item_id) REFERENCES caixa_item (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS insumo(
	id INTEGER PRIMARY KEY NOT NULL,
	item_id INTEGER NOT NULL,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS insumo_item(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd_insumo_item INTEGER NOT NULL,
	data_alteracao DATETIME NOT NULL,
	insumo_id INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	FOREIGN KEY (insumo_id) REFERENCES insumo (id) ON UPDATE CASCADE,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE CASCADE
);

-- TRIGGERS ITEM

CREATE TRIGGER IF NOT EXISTS insert_item
	BEFORE INSERT
	ON item
	WHEN EXISTS(SELECT 1 FROM item WHERE nome = NEW.nome AND NEW.status_item_id > 0 LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Item com mesmo "nome" ja existe!');
	END;

CREATE TRIGGER IF NOT EXISTS update_item
	BEFORE UPDATE
	ON item
	BEGIN
		INSERT INTO item (nome, qtd, custo, preco, data_alteracao, item_antes_id, status_item_id, unidade_medida_id)
			VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, NEW.data_alteracao, OLD.id, NEW.status_item_id, NEW.unidade_medida_id);
		
		UPDATE item
		SET status_item_id = -OLD.status_item_id
		WHERE id = OLD.id;
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS delete_item
	BEFORE DELETE
	ON item
	BEGIN
		SELECT RAISE(ROLLBACK, 'Item nao pode ser deletado, altere "status_item_id" para seu inverso!');
	END;

-- TRIGGER CAIXA

CREATE TRIGGER IF NOT EXISTS insert_caixa
	BEFORE INSERT
	ON caixa
	WHEN EXISTS(SELECT 1 FROM caixa WHERE data_fechamento = NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Ainda ha um caixa aberto!');
	END;

-- VIEWS COM REDUNDANCIAS

CREATE VIEW IF NOT EXISTS item_view (
	id,
	nome,
	qtd_item,
	custo_item,
	preco_item,
	data_alteracao,
	status,
	unidade_medida
) AS
	SELECT  i.id, i.nome, i.qtd, i.custo, i.preco, i.data_alteracao,
	si.nome,
	um.nome
	FROM item AS i
	INNER JOIN status_item AS si ON si.id = i.status_item_id 
	INNER JOIN unidade_medida AS um ON um.id = i.unidade_medida_id;

CREATE VIEW IF NOT EXISTS caixa_item_view (
	id,
	item_id,
	nome_item,
	qtd_caixa_item,
	custo_item,
	preco_item,
	status_item,
	unidade_medida,
	caixa_id,
	data_abertura,
	estoque_id,
	estoque_data
) AS
	SELECT ci.id,
			iv.id, iv.nome,
			ci.qtd,
			iv.custo_item, iv.preco_item, iv.status, iv.unidade_medida,
			c.id, c.data_abertura,
			e.id, e.data_alteracao
	FROM caixa_item AS ci
	INNER JOIN item_view AS iv ON iv.id = ci.item_id
	INNER JOIN caixa AS c ON c.id = ci.caixa_id
		INNER JOIN estoque AS e ON e.id = c.estoque_id;
	
CREATE VIEW IF NOT EXISTS venda_item_view (
	id,
	item_id,
	caixa_item_id,
	nome_item,
	qtd_venda_item,
	preco_venda_item,
	custo_item,
	preco_item,
	status_item,
	unidade_medida,
	desconto_venda_item,
	venda_id,
	data_pagamento
) AS
	SELECT vi.id,
	civ.item_id, civ.id, civ.nome_item,
	vi.qtd, vi.preco_venda,
	civ.custo_item, civ.preco_item, civ.status_item, civ.unidade_medida,
	civ.preco_item-vi.preco_venda,
	v.id, v.data_pagamento
	FROM venda_item AS vi
	INNER JOIN caixa_item_view AS civ ON civ.id = vi.caixa_item_id
	INNER JOIN venda AS v ON v.id = vi.venda_id;

-- VIEWSS DE ULTIMOS

CREATE VIEW IF NOT EXISTS item_atual 
AS
	SELECT iv.* FROM item_view AS iv
	INNER JOIN item AS i ON i.id = iv.id
	WHERE i.status_item_id > 0;

CREATE VIEW IF NOT EXISTS caixa_item_aberto
AS
	SELECT civ.* FROM caixa_item_view AS civ
	INNER JOIN caixa AS c ON c.id = civ.caixa_id
	WHERE c.data_fechamento = NULL;

-- INSERTS BASICOS

INSERT INTO status_item (id, nome)
	VALUES	(-3, 'APENAS_INSUMO_ANTERIOR'),
			(-2, 'INATIVO_ANTERIOR'),
			(-1, 'ATIVO_ANTERIOR'),
			(0, 'INDEFINIDO'),
			(1, 'ATIVO'),
			(2, 'INATIVO'),
			(3, 'APENAS_INSUMO');

INSERT INTO unidade_medida (nome)
	VALUES	('UNIDADES'),
			('KILOS'),
			('GRAMAS'),
			('LITROS'),
			('MILILITROS'),
			('METROS'),
			('PUNHADO'),
			('LAPADA');

INSERT INTO forma_pagamento (nome)
	VALUES	('DINHEIRO'),
			('PIX'),
			('CARTAO_DEBITO'),
			('CARTAO_CREDITO');

