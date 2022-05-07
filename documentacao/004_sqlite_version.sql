-- DROP EM TUDO

DROP TABLE IF EXISTS config;

DROP TABLE IF EXISTS estoque_item;

DROP TABLE IF EXISTS estoque;

DROP TABLE IF EXISTS venda_item;

DROP TABLE IF EXISTS caixa_item;

DROP TABLE IF EXISTS venda;

DROP TABLE IF EXISTS caixa;

DROP VIEW IF EXISTS caixa_atual;

DROP TABLE IF EXISTS forma_pagamento;

DROP TABLE IF EXISTS receita_item;

DROP TABLE IF EXISTS insumo;

DROP TABLE IF EXISTS unidade_medida;

DROP TABLE IF EXISTS status_item;

DROP VIEW IF EXISTS item_atual;

DROP TABLE IF EXISTS item;

-- CREATES

CREATE TABLE IF NOT EXISTS config(
	id INTEGER PRIMARY KEY CHECK (id = 0),
	usuario TEXT DEFAULT NULL,	
	logado BOOLEAN NOT NULL DEFAULT false,
	backup_schedule_hrs INTEGER NOT NULL DEFAULT 0,
	moeda TEXT NOT NULL DEFAULT 'R$',
	tem_insumo BOOLEAN NOT NULL DEFAULT false
);

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

-- VIEWS E TRIGGERS
		
CREATE TRIGGER IF NOT EXISTS insert_item
	BEFORE INSERT
	ON item
	WHEN EXISTS(SELECT 1 FROM item WHERE nome = NEW.nome AND NEW.status_item_id > 0 LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'item with the same "nome" already exists');
	END;

CREATE VIEW IF NOT EXISTS item_atual 
AS
	SELECT * FROM item
	WHERE status_item_id > 0;
	
CREATE TRIGGER IF NOT EXISTS update_item
	INSTEAD OF UPDATE
	ON item_atual
	BEGIN
		INSERT INTO item (nome, qtd, custo, preco, status_item_id, data_alteracao, item_antes_id, unidade_medida_id)
			VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, NEW.status_item_id, datetime('now'), OLD.id, NEW.unidade_medida_id);
		
		UPDATE item
		SET status_item_id = -OLD.status_item_id
		WHERE id = OLD.id;
	END;

CREATE TRIGGER IF NOT EXISTS delete_item
	INSTEAD OF DELETE
	ON item_atual
	BEGIN
		UPDATE item
		SET status_item_id = -OLD.status_item_id
		WHERE id = OLD.item_antes_id;
	
		DELETE FROM item
		WHERE id = OLD.id;
	END;

CREATE VIEW caixa_atual
AS
	SELECT * FROM caixa
	WHERE data_fechamento = NULL
	LIMIT 1;
	
-- INSERTS BASICOS

INSERT INTO config (id) VALUES (0);

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