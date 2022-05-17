-- DROP EM TUDO

DROP VIEW IF EXISTS venda_item_view;

DROP VIEW IF EXISTS caixa_item_aberto;

DROP VIEW IF EXISTS item_atual;

DROP VIEW IF EXISTS caixa_item_view;

DROP VIEW IF EXISTS item_view;

DROP TABLE IF EXISTS caixa_item;

DROP TABLE IF EXISTS caixa;

DROP TABLE IF EXISTS caixa_fundo;

DROP TABLE IF EXISTS venda;

DROP TABLE IF EXISTS venda_local;

DROP TABLE IF EXISTS forma_pagamento;

DROP TABLE IF EXISTS insumo;

DROP TABLE IF EXISTS formula;

DROP TABLE IF EXISTS item;

DROP TABLE IF EXISTS estoque;

DROP TABLE IF EXISTS unidade_medida;

DROP TABLE IF EXISTS estoque_item;

DROP TABLE IF EXISTS venda_item;


-- CREATES

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
	data_alteracao DATETIME NOT NULL UNIQUE DEFAULT (datetime())
);

CREATE TABLE IF NOT EXISTS item(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	qtd INTEGER NOT NULL,
	custo NUMERIC NOT NULL,
	preco NUMERIC,
	ativo BOOLEAN NOT NULL DEFAULT (true) CHECK (ativo IN (false, true)),
	atual BOOLEAN NOT NULL DEFAULT (true) CHECK (atual IN (false, true)),
	data_alteracao DATETIME NOT NULL DEFAULT (datetime()),
	item_antes_id INTEGER DEFAULT NULL,	
	unidade_medida_id INTEGER NOT NULL,
	formula_id INTEGER DEFAULT NULL,
	FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (formula_id) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS estoque_item(
	estoque_id INTEGER NOT NULL,
	item_id INTEGER NOT NULL,
	PRIMARY KEY (estoque_id, item_id),
	FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT
) WITHOUT ROWID;

CREATE TABLE IF NOT EXISTS caixa(
	id INTEGER PRIMARY KEY NOT NULL,
	data_abertura DATETIME NOT NULL DEFAULT (datetime()),
	data_fechamento DATETIME,
	estoque_id INTEGER NOT NULL,
	FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS caixa_fundo(
	id INTEGER PRIMARY KEY NOT NULL,
	valor NUMERIC NOT NULL,
	data_alteracao DATETIME NOT NULL DEFAULT (datetime()),
	caixa_id INTEGER NOT NULL,
	FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS caixa_item(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	data_alteracao DATETIME NOT NULL DEFAULT (datetime()),
	item_id INTEGER NOT NULL,
	caixa_id INTEGER NOT NULL,
	FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS venda_local(
	id INTEGER PRIMARY KEY NOT NULL,
	apelido TEXT NOT NULL UNIQUE,
	endereco TEXT NOT NULL,
	complemento TEXT
);

CREATE TABLE IF NOT EXISTS venda(
	id INTEGER PRIMARY KEY NOT NULL,
	valor_pago NUMERIC NOT NULL,
	valor_venda NUMERIC NOT NULL,
	data_pagamento DATETIME NOT NULL DEFAULT (datetime()),
	venda_local_id INTEGER NOT NULL,
	forma_pagamento_id INTEGER NOT NULL,
	caixa_id INTEGER NOT NULL,
	FOREIGN KEY (venda_local_id) REFERENCES venda_local (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS venda_item(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	preco_venda NUMERIC,
	venda_id INTEGER,
	caixa_item_id INTEGER NOT NULL,
	FOREIGN KEY (venda_id) REFERENCES venda (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixa_item_id) REFERENCES caixa_item (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS formula(
	id INTEGER PRIMARY KEY NOT NULL
);

CREATE TABLE IF NOT EXISTS insumo(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd_insumo_item INTEGER NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT (true) CHECK (ativo IN (false, true)),
	data_alteracao DATETIME NOT NULL DEFAULT (datetime()),
	formula_id INTEGER NOT NULL,
	insumo_id INTEGER NOT NULL,
	FOREIGN KEY (formula_id) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (insumo_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);


-- TRIGGERS ITEM

CREATE TRIGGER IF NOT EXISTS validate_atual_insert_item
	BEFORE INSERT
	ON item
	WHEN NEW.atual = false
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em "item" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_nome_insert_item
	BEFORE INSERT
	ON item
	WHEN EXISTS(SELECT 1 FROM item WHERE nome = NEW.nome AND NEW.atual = true LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, '"item" com mesmo "nome" já existe!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_atual_update_item
	BEFORE UPDATE
	ON item
	WHEN OLD.atual = false
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "item" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_delete_item
	BEFORE DELETE
	ON item
	BEGIN
		SELECT RAISE(ROLLBACK, '"item" nao pode ser deletado, desative-o trocando "ativo" para "false"!');
	END;

CREATE TRIGGER IF NOT EXISTS update_item
	BEFORE UPDATE
	ON item
	WHEN OLD.atual = true
	BEGIN
		UPDATE item
		SET atual = false
		WHERE id = OLD.id;
	
		INSERT INTO item (nome, qtd, custo, preco, item_antes_id, ativo, unidade_medida_id)
			VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, OLD.id, NEW.ativo, NEW.unidade_medida_id);
		
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS CAIXA_ITEM

CREATE TRIGGER IF NOT EXISTS validate_caixa_fundo_insert_caixa_item
	BEFORE INSERT
	ON caixa_item
	WHEN NOT EXISTS(SELECT 1 FROM caixa_fundo WHERE caixa_id = NEW.caixa_id LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Antes de inserir um "caixa_item" é necessário inserir um "caixa_fundo"!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_update_caixa_item
	BEFORE UPDATE
	ON caixa_item
	WHEN OLD.id IN (SELECT id FROM caixa_item WHERE data_alteracao < (SELECT MAX(data_alteracao) FROM caixa_item WHERE item_id = OLD.item_id))
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em "caixa_item" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_delete_caixa_item
	BEFORE DELETE
	ON caixa_item
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixa_item" nao pode ser deletado, desative-o trocando "ativo" para "false"!');
	END;

CREATE TRIGGER IF NOT EXISTS after_insert_caixa_item
	AFTER INSERT
	ON caixa_item
	WHEN EXISTS(SELECT 1 FROM caixa_fundo WHERE caixa_id = NEW.caixa_id LIMIT 1)
	BEGIN
		INSERT INTO caixa_fundo (valor, caixa_id)
			SELECT valor, caixa_id FROM caixa_fundo AS cf
				WHERE cf.data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_fundo WHERE caixa_id = NEW.caixa_id) LIMIT 1;
	END;

CREATE TRIGGER IF NOT EXISTS update_caixa_item
	BEFORE UPDATE
	ON caixa_item
	WHEN OLD.id IN (SELECT id FROM caixa_item WHERE data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_item WHERE item_id = OLD.item_id))
	BEGIN
		INSERT INTO caixa_item (qtd, item_id, caixa_id)
			VALUES (NEW.qtd, OLD.item_id, OLD.caixa_id);
				
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS CAIXA_FUNDO

CREATE TRIGGER IF NOT EXISTS validate_duplicado_insert_caixa_fundo
	BEFORE INSERT
	ON caixa_fundo
	WHEN EXISTS(SELECT 1 FROM caixa_fundo WHERE data_alteracao = NEW.data_alteracao LIMIT 1)
	BEGIN 
		SELECT RAISE(IGNORE);
	END;
	

-- TRIGGERS ASSOCIADOS A CAIXA

CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_item
	BEFORE INSERT
	ON item
	WHEN EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "item" com um "caixa" aberto!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_caixa_item
	BEFORE INSERT
	ON caixa_item
	WHEN EXISTS(SELECT 1 FROM caixa WHERE NEW.caixa_id = id AND data_fechamento IS NOT NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "caixa_item" com um "caixa" fechado!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_caixa_fundo
	BEFORE INSERT
	ON caixa_fundo
	WHEN EXISTS(SELECT 1 FROM caixa WHERE NEW.caixa_id = id AND data_fechamento IS NOT NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "caixa_fundo" com um "caixa" fechado!');
	END;


-- TRIGGERS CAIXA

CREATE TRIGGER IF NOT EXISTS validate_aberto_insert_caixa
	BEFORE INSERT
	ON caixa
	WHEN EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Ainda há um "caixa" aberto!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_update_caixa
	BEFORE UPDATE
	ON caixa
	WHEN	OLD.id <> NEW.id
		OR	OLD.data_abertura <> NEW.data_abertura
		OR	OLD.estoque_id <> NEW.estoque_id
		OR	OLD.data_fechamento IS NOT NULL
	BEGIN
		SELECT RAISE(ROLLBACK, 'UPDATE em "caixa" desautorizado! Você tentou mudar "id", "data_abertura", "estoque_id" ou alterar um "caixa" já fechado.');
	END;

CREATE TRIGGER IF NOT EXISTS insert_caixa
	BEFORE INSERT
	ON caixa
	WHEN NOT EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)
	BEGIN
		INSERT INTO estoque (data_alteracao) VALUES (datetime());
		
		INSERT INTO estoque_item (estoque_id, item_id)
			SELECT e.id, i.id
				FROM estoque AS e, item AS i
				WHERE
					e.data_alteracao = (SELECT MAX(data_alteracao) FROM estoque)
						AND
					i.atual = true;
		
		INSERT INTO caixa (estoque_id)
			SELECT e.id
				FROM estoque AS e
				WHERE
					e.data_alteracao =  (SELECT MAX(data_alteracao) FROM estoque);
		
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS VENDA_ITEM

CREATE TRIGGER IF NOT EXISTS validate_update_venda_item
	BEFORE UPDATE
	ON venda_item
	WHEN OLD.venda_id IS NOT NULL
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "venda_item" de venda executada!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_delete_venda_item
	BEFORE DELETE
	ON venda_item
	WHEN OLD.venda_id IS NOT NULL
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em "venda_item" de venda executada!');
	END;


-- TRIGGERS VENDA

CREATE TRIGGER IF NOT EXISTS validate_update_venda
	BEFORE UPDATE
	ON venda
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "venda"!');
	END;

CREATE TRIGGER IF NOT EXISTS validate_delete_venda
	BEFORE DELETE
	ON venda
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em "venda"!');
	END;


-- VIEWS COM REDUNDANCIAS

CREATE VIEW IF NOT EXISTS item_view (
	id,
	nome,
	qtd_item,
	custo_item,
	preco_item,
	ativo,
	atual,
	data_alteracao,
	unidade_medida
) AS
	SELECT
	i.id,
	i.nome,
	i.qtd,
	i.custo,
	i.preco,
	i.ativo,
	i.atual,
	i.data_alteracao,
	um.nome
	FROM item AS i
	INNER JOIN unidade_medida AS um ON um.id = i.unidade_medida_id;

CREATE VIEW IF NOT EXISTS caixa_item_view (
	id,
	item_id,
	nome_item,
	qtd_caixa_item,
	data_alteracao,
	custo_item,
	preco_item,
	unidade_medida,
	caixa_id,
	caixa_data_abertura,
	caixa_data_fechamento,
	fundo_de_caixa,
	estoque_id,
	estoque_data
) AS
	SELECT	
	ci.id,
	iv.id,
	iv.nome,
	ci.qtd,
	ci.data_alteracao,
	iv.custo_item,
	iv.preco_item,
	iv.unidade_medida,
	c.id,
	c.data_abertura,
	c.data_fechamento,
	cf.valor,
	e.id,
	e.data_alteracao
	FROM caixa_item AS ci
	INNER JOIN item_view AS iv ON iv.id = ci.item_id
	INNER JOIN caixa AS c ON c.id = ci.caixa_id
		INNER JOIN caixa_fundo AS cf ON cf.caixa_id = c.id
			AND
		cf.data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_fundo WHERE caixa_id = c.id)
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
	unidade_medida,
	desconto_venda_item,
	venda_id,
	venda_valor_pago,
	venda_valor_venda,
	data_pagamento,
	local_de_venda,
	forma_pagamento
) AS
	SELECT
	vi.id,
	civ.item_id,
	civ.id,
	civ.nome_item,
	vi.qtd,
	vi.preco_venda,
	civ.custo_item,
	civ.preco_item,
	civ.unidade_medida,
	civ.preco_item - vi.preco_venda,
	v.id,
	v.valor_pago,
	v.valor_venda,
	v.data_pagamento,
	vl.apelido,
	fp.nome
	FROM venda_item AS vi
	INNER JOIN caixa_item_view AS civ ON civ.id = vi.caixa_item_id
	INNER JOIN venda AS v ON v.id = vi.venda_id
		INNER JOIN venda_local AS vl ON vl.id = v.venda_local_id
		INNER JOIN forma_pagamento AS fp ON fp.id = v.forma_pagamento_id;


-- VIEWS DE ULTIMOS

CREATE VIEW IF NOT EXISTS item_atual 
AS
	SELECT iv.* FROM item_view AS iv
	INNER JOIN item AS i ON i.id = iv.id
	WHERE i.atual = true;

CREATE VIEW IF NOT EXISTS caixa_item_atual 
AS
	SELECT * FROM caixa_item_view AS civ
	WHERE civ.data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_item WHERE civ.item_id = item_id);

CREATE VIEW IF NOT EXISTS caixa_item_aberto
AS
	SELECT cia.* FROM caixa_item_atual AS cia
	INNER JOIN caixa AS c ON c.id = cia.caixa_id
	WHERE c.data_fechamento IS NULL;


-- INSERTS BASICOS

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