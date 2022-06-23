-- DROP EM TUDO

DROP TABLE IF EXISTS OperationActive;

DROP VIEW IF EXISTS VendaProdutoView;

DROP VIEW IF EXISTS CaixaProdutoAberto;

DROP VIEW IF EXISTS ProdutoAtual;

DROP VIEW IF EXISTS CaixaProdutoView;

DROP VIEW IF EXISTS ProdutoView;

DROP TABLE IF EXISTS VendaProduto;

DROP TABLE IF EXISTS Venda;

DROP TABLE IF EXISTS FormaPagamento;

DROP TABLE IF EXISTS VendaLocal;

DROP TABLE IF EXISTS CaixaProduto;

DROP TABLE IF EXISTS CaixaFundo;

DROP TABLE IF EXISTS Caixa;

DROP TABLE IF EXISTS EstoqueProduto;

DROP TABLE IF EXISTS Estoque;

DROP TABLE IF EXISTS Insumo;

DROP TABLE IF EXISTS GastoProduto;

DROP TABLE IF EXISTS Gasto;

DROP TABLE IF EXISTS Produto;

DROP TABLE IF EXISTS Formula;

DROP TABLE IF EXISTS UnidadeMedida;


-- PRAGA DO PRAGMA

PRAGMA foreign_keys = OFF;

PRAGMA foreign_keys = ON;


-- CREATES

CREATE TABLE IF NOT EXISTS UnidadeMedida(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Formula(
	id INTEGER PRIMARY KEY NOT NULL,
	aberta BOOLEAN NOT NULL DEFAULT 0 CHECK (aberta IN (0, 1))
);

CREATE TABLE IF NOT EXISTS Produto(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	qtd INTEGER NOT NULL,
	custo INTEGER NOT NULL,
	preco INTEGER,
	ativo BOOLEAN NOT NULL DEFAULT 1 CHECK (ativo IN (0, 1)),
	atual BOOLEAN NOT NULL DEFAULT 1 CHECK (atual IN (0, 1)),
	dataAlteracao TEXT NOT NULL DEFAULT (datetime()),
	produtoAntesId INTEGER DEFAULT NULL,
	unidadeMedidaId INTEGER NOT NULL,
	formulaId INTEGER DEFAULT NULL,
	FOREIGN KEY (unidadeMedidaId) REFERENCES unidadeMedida (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (formulaId) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Gasto(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL,
	custo INTEGER NOT NULL,
	dataPagamento TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS GastoProduto(
	gastoId INTEGER NOT NULL,
	produtoId INTEGER NOT NULL,
	PRIMARY KEY (gastoId, produtoId),
	FOREIGN KEY (gastoId) REFERENCES gasto (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (produtoId) REFERENCES produto (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Insumo(
	id INTEGER PRIMARY KEY NOT NULL,
	qtdInsumoProduto INTEGER NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT 1 CHECK (ativo IN (0, 1)),
	atual BOOLEAN NOT NULL DEFAULT 1 CHECK (atual IN (0, 1)),
	dataAlteracao TEXT NOT NULL DEFAULT (datetime()),
	formulaId INTEGER NOT NULL,
	insumoId INTEGER NOT NULL,
	FOREIGN KEY (formulaId) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (insumoId) REFERENCES produto (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Estoque(
	id INTEGER PRIMARY KEY NOT NULL,
	dataAlteracao TEXT NOT NULL UNIQUE DEFAULT (datetime())
);

CREATE TABLE IF NOT EXISTS EstoqueProduto(
	estoqueId INTEGER NOT NULL,
	produtoId INTEGER NOT NULL,
	PRIMARY KEY (estoqueId, produtoId),
	FOREIGN KEY (estoqueId) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (produtoId) REFERENCES produto (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Caixa(
	id INTEGER PRIMARY KEY NOT NULL,
	dataAbertura TEXT NOT NULL DEFAULT (datetime()),
	dataFechamento TEXT,
	estoqueId INTEGER NOT NULL,
	FOREIGN KEY (estoqueId) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS CaixaFundo(
	id INTEGER PRIMARY KEY NOT NULL,
	valor INTEGER NOT NULL,
	dataAlteracao TEXT NOT NULL DEFAULT (datetime()),
	caixaId INTEGER NOT NULL,
	FOREIGN KEY (caixaId) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS CaixaProduto(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	ativo BOOLEAN NOT NULL DEFAULT 1 CHECK (ativo IN (0, 1)),
	atual BOOLEAN NOT NULL DEFAULT 1 CHECK (atual IN (0, 1)),
	dataAlteracao TEXT NOT NULL DEFAULT (datetime()),
	produtoId INTEGER NOT NULL,
	caixaId INTEGER NOT NULL,
	FOREIGN KEY (produtoId) REFERENCES produto (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixaId) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS VendaLocal(
	id INTEGER PRIMARY KEY NOT NULL,
	apelido TEXT NOT NULL UNIQUE,
	endereco TEXT NOT NULL,
	complemento TEXT,
	latitude REAL,
	longitude REAL
);

CREATE TABLE IF NOT EXISTS FormaPagamento(
	id INTEGER PRIMARY KEY NOT NULL,
	nome TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Venda(
	id INTEGER PRIMARY KEY NOT NULL,
	valorPago INTEGER NOT NULL,
	valorVenda INTEGER NOT NULL,
	aberta BOOLEAN NOT NULL DEFAULT 1 CHECK (aberta IN (0, 1)),
	dataPagamento TEXT NOT NULL DEFAULT (datetime()),
	vendaLocalId INTEGER NOT NULL,
	formaPagamentoId INTEGER NOT NULL,
	caixaId INTEGER NOT NULL,
	FOREIGN KEY (vendaLocalId) REFERENCES vendaLocal (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (formaPagamentoId) REFERENCES formaPagamento (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixaId) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS VendaProduto(
	id INTEGER PRIMARY KEY NOT NULL,
	qtd INTEGER NOT NULL,
	precoVenda INTEGER,
	vendaId INTEGER,
	caixaProdutoId INTEGER NOT NULL,
	FOREIGN KEY (vendaId) REFERENCES venda (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
	FOREIGN KEY (caixaProdutoId) REFERENCES caixaProduto (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);


-- CREATE TABLE AND TRIGGER FOR OPERATION

CREATE TABLE IF NOT EXISTS OperationActive(
	recursionLayer INTEGER PRIMARY KEY NOT NULL DEFAULT 1
);

CREATE TRIGGER IF NOT EXISTS ValidateInsertOperationActive
	BEFORE INSERT
	ON OperationActive
	WHEN EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível começar uma nova operação recursiva antes de acabar uma! Tente incrementar o "recursionLayer" se estiver mergulhandoo em recursões.');
	END;


-- TRIGGERS PRODUTO

CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertProduto
	BEFORE INSERT
	ON Produto
	WHEN NEW.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com "produto" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateNomeInsertProduto
	BEFORE INSERT
	ON Produto
	WHEN EXISTS(SELECT 1 FROM Produto WHERE nome = NEW.nome AND atual = 1 LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, '"produto" com "ativo" 1 e mesmo "nome" já existe!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateFormulaUpdateProduto
	BEFORE UPDATE
	ON Produto
	WHEN OLD.formulaId <> NEW.formulaId
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "formulaId" de "produto"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateAtualUpdateProduto
	BEFORE UPDATE
	ON Produto
	WHEN OLD.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "produto" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteProduto
	BEFORE DELETE
	ON Produto
	BEGIN
		SELECT RAISE(ROLLBACK, '"produto" nao pode ser deletado, desative-o trocando "ativo" para "0"!');
	END;

CREATE TRIGGER IF NOT EXISTS InsertProduto
	BEFORE INSERT
	ON Produto
	WHEN NEW.formulaId IS NULL AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO Formula (aberta)
			VALUES (0);
		
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
		
		INSERT INTO Produto (nome, qtd, custo, preco, ativo, unidadeMedidaId, formulaId)
			VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, NEW.ativo, NEW.unidadeMedidaId, (SELECT MAX(id) FROM Formula));

		DELETE FROM OperationActive;
		
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS UpdateProduto
	BEFORE UPDATE
	ON Produto
	WHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);

		UPDATE Produto
		SET atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
	
		INSERT INTO Produto (nome, qtd, custo, preco, produtoAntesId, ativo, unidadeMedidaId, formulaId)
			VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, OLD.id, NEW.ativo, NEW.unidadeMedidaId, OLD.formulaId);
		
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS UpdateDeleteProduto
	BEFORE UPDATE
	ON Produto
	WHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);

		UPDATE Produto
		SET	ativo = 0,
			atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
		
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS CAIXA_PRODUTO

CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertCaixaProduto
	BEFORE INSERT
	ON Produto
	WHEN NEW.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com "caixaProduto" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateProdutoIdInsertCaixaProduto
	BEFORE INSERT
	ON CaixaProduto
	WHEN EXISTS(SELECT 1 FROM CaixaProduto WHERE produtoId = NEW.produtoId AND atual = 1 LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixaProduto" com "ativo" 1 e mesmo "produtoId" já existe!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateCaixaFundoInsertCaixaProduto
	BEFORE INSERT
	ON CaixaProduto
	WHEN NOT EXISTS(SELECT 1 FROM CaixaFundo WHERE caixaId = NEW.caixaId LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Antes de inserir um "caixaProduto" é necessário inserir um "caixaFundo"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixaProduto
	BEFORE UPDATE
	ON CaixaProduto
	WHEN OLD.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "caixaProduto" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixaProduto
	BEFORE DELETE
	ON CaixaProduto
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixaProduto" nao pode ser deletado, desative-o trocando "ativo" para "0"!');
	END;

CREATE TRIGGER IF NOT EXISTS AfterInsertCaixaProduto
	AFTER INSERT
	ON CaixaProduto
	WHEN EXISTS(SELECT 1 FROM CaixaFundo WHERE caixaId = NEW.caixaId LIMIT 1)
	BEGIN
		INSERT INTO CaixaFundo (valor, caixaId)
			SELECT valor, caixaId FROM CaixaFundo AS cf
				WHERE cf.dataAlteracao = (SELECT MAX(dataAlteracao) FROM CaixaFundo WHERE caixaId = NEW.caixaId) LIMIT 1;
	END;

CREATE TRIGGER IF NOT EXISTS UpdateCaixaProduto
	BEFORE UPDATE
	ON CaixaProduto
	WHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
		
		UPDATE CaixaProduto
		SET atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
		
		INSERT INTO CaixaProduto (qtd, ativo, produtoId, caixaId)
			VALUES (NEW.qtd, NEW.ativo, OLD.produtoId, OLD.caixaId);
				
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS UpdateDeleteCaixaProduto
	BEFORE UPDATE
	ON CaixaProduto
	WHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
		
		UPDATE CaixaProduto
		SET	ativo = 0,
			atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
				
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS CAIXA_FUNDO

CREATE TRIGGER IF NOT EXISTS ValidateDuplicadoInsertCaixaFundo
	BEFORE INSERT
	ON CaixaFundo
	WHEN EXISTS(SELECT 1 FROM CaixaFundo WHERE dataAlteracao = NEW.dataAlteracao LIMIT 1)
	BEGIN 
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixaFundo
	BEFORE UPDATE
	ON CaixaFundo
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixaFundo" nao pode ser atualizado, insira outro caixa fundo relacionado ao mesmo caixa!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixaFundo
	BEFORE DELETE
	ON CaixaFundo
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixaFundo" nao pode ser deletado!');
	END;
	

-- TRIGGERS ASSOCIADOS A CAIXA

CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertProduto
	BEFORE INSERT
	ON Produto
	WHEN EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "produto" com um "caixa" aberto!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertCaixaProduto
	BEFORE INSERT
	ON CaixaProduto
	WHEN EXISTS(SELECT 1 FROM Caixa WHERE NEW.caixaId = id AND dataFechamento IS NOT NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "caixaProduto" com um "caixa" fechado!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertCaixaFundo
	BEFORE INSERT
	ON CaixaFundo
	WHEN EXISTS(SELECT 1 FROM Caixa WHERE NEW.caixaId = id AND dataFechamento IS NOT NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em "caixaFundo" com um "caixa" fechado!');
	END;


-- TRIGGERS CAIXA

CREATE TRIGGER IF NOT EXISTS ValidateAbertoInsertCaixa
	BEFORE INSERT
	ON Caixa
	WHEN EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, 'Ainda há um "caixa" aberto!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixa
	BEFORE UPDATE
	ON Caixa
	WHEN	OLD.id <> NEW.id
		OR	OLD.dataAbertura <> NEW.dataAbertura
		OR	OLD.estoqueId <> NEW.estoqueId
		OR	OLD.dataFechamento IS NOT NULL
	BEGIN
		SELECT RAISE(ROLLBACK, 'UPDATE em "caixa" desautorizado! Você tentou mudar "id", "dataAbertura", "estoqueId" ou alterar um "caixa" já fechado.');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixa
	BEFORE DELETE
	ON Caixa
	BEGIN
		SELECT RAISE(ROLLBACK, '"caixa" nao pode ser deletado!');
	END;

CREATE TRIGGER IF NOT EXISTS InsertCaixa
	BEFORE INSERT
	ON Caixa
	WHEN NOT EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1) AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO Estoque (dataAlteracao) VALUES (datetime());
		
		INSERT INTO EstoqueProduto (estoqueId, produtoId)
			SELECT e.id, i.id
				FROM Estoque AS e, produto AS i
				WHERE
					e.dataAlteracao = (SELECT MAX(dataAlteracao) FROM Estoque)
						AND
					i.atual = 1;
		
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
				
		INSERT INTO Caixa (estoqueId)
			SELECT e.id
				FROM Estoque AS e
				WHERE
					e.dataAlteracao =  (SELECT MAX(dataAlteracao) FROM Estoque);
	
		DELETE FROM OperationActive;
		
		SELECT RAISE(IGNORE);
	END;


-- TRIGGERS VENDA_PRODUTO

CREATE TRIGGER IF NOT EXISTS ValidateInsertVendaProduto
	BEFORE INSERT
	ON VendaProduto
	WHEN (SELECT aberta FROM Venda WHERE id = NEW.vendaId) = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em "vendaProduto" de venda executada!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateUpdateVendaProduto
	BEFORE UPDATE
	ON VendaProduto
	WHEN OLD.vendaId IS NOT NULL OR (SELECT aberta FROM Venda WHERE id = OLD.vendaId) = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "vendaProduto" de venda executada!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteVendaProduto
	BEFORE DELETE
	ON VendaProduto
	WHEN OLD.vendaId IS NOT NULL OR (SELECT aberta FROM Venda WHERE id = OLD.vendaId) = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em "vendaProduto" de venda executada!');
	END;


-- TRIGGERS VENDA

CREATE TRIGGER IF NOT EXISTS ValidateUpdateVenda
	BEFORE UPDATE
	ON Venda
	WHEN OLD.aberta = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "venda"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteVenda
	BEFORE DELETE
	ON Venda
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em "venda"!');
	END;


-- TRIGGERS INSUMO

CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertInsumo
	BEFORE INSERT
	ON Insumo
	WHEN NEW.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com "insumo" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateFormulaInsumoIdsInsertInsumo
	BEFORE INSERT
	ON Insumo
	WHEN EXISTS(SELECT 1 FROM Insumo WHERE formulaId = NEW.formulaId AND insumoId = NEW.insumoId AND atual = 1 LIMIT 1)
	BEGIN
		SELECT RAISE(ROLLBACK, '"insumo" com "ativo" 1 e mesmos "formulaId" e "insumoId" já existe!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateUpdateInsumo
	BEFORE UPDATE
	ON Insumo
	WHEN OLD.atual = 0
	BEGIN
		SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em "insumo" não "atual"!');
	END;

CREATE TRIGGER IF NOT EXISTS ValidateDeleteInsumo
	BEFORE DELETE
	ON Insumo
	BEGIN
		SELECT RAISE(ROLLBACK, '"insumo" nao pode ser deletado, desative-o trocando "ativo" para "0"!');
	END;

CREATE TRIGGER IF NOT EXISTS UpdateInsumo
	BEFORE UPDATE
	ON Insumo
	WHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
		
		UPDATE Insumo
		SET atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
		
		INSERT INTO Insumo (qtdInsumoProduto, ativo, formulaId, insumoId)
			VALUES (NEW.qtdInsumoProduto, NEW.ativo, OLD.formulaId, OLD.insumoId);
				
		SELECT RAISE(IGNORE);
	END;

CREATE TRIGGER IF NOT EXISTS UpdateDeleteInsumo
	BEFORE UPDATE
	ON Insumo
	WHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)
	BEGIN
		INSERT INTO OperationActive (recursionLayer)
			VALUES (1);
		
		UPDATE Insumo
		SET	ativo = 0,
			atual = 0
		WHERE id = OLD.id;
	
		DELETE FROM OperationActive;
				
		SELECT RAISE(IGNORE);
	END;


-- VIEWS COM REDUNDANCIAS

CREATE VIEW IF NOT EXISTS ProdutoView (
	id,
	nome,
	qtdProduto,
	custoProduto,
	precoProduto,
	ativo,
	atual,
	dataAlteracao,
	unidadeMedida
) AS
	SELECT
	i.id,
	i.nome,
	i.qtd,
	i.custo,
	i.preco,
	i.ativo,
	i.atual,
	i.dataAlteracao,
	um.nome
	FROM Produto AS i
	INNER JOIN UnidadeMedida AS um ON Um.id = i.unidadeMedidaId;

CREATE VIEW IF NOT EXISTS CaixaProdutoView (
	id,
	produtoId,
	nomeProduto,
	qtdCaixaProduto,
	ativo,
	atual,
	dataAlteracao,
	custoProduto,
	precoProduto,
	unidadeMedida,
	caixaId,
	caixaDataAbertura,
	caixaDataFechamento,
	fundoDeCaixa,
	estoqueId,
	estoqueData
) AS
	SELECT	
	ci.id,
	iv.id,
	iv.nome,
	ci.qtd,
	ci.ativo,
	ci.atual,
	ci.dataAlteracao,
	iv.custoProduto,
	iv.precoProduto,
	iv.unidadeMedida,
	c.id,
	c.dataAbertura,
	c.dataFechamento,
	cf.valor,
	e.id,
	e.dataAlteracao
	FROM CaixaProduto AS ci
	INNER JOIN ProdutoView AS iv ON Iv.id = ci.produtoId
	INNER JOIN Caixa AS c ON c.id = ci.caixaId
		INNER JOIN CaixaFundo AS cf ON Cf.caixaId = c.id
			AND
		cf.dataAlteracao = (SELECT MAX(dataAlteracao) FROM CaixaFundo WHERE caixaId = c.id)
		INNER JOIN Estoque AS e ON e.id = c.estoqueId;
	
CREATE VIEW IF NOT EXISTS VendaProdutoView (
	id,
	produtoId,
	caixaProdutoId,
	nomeProduto,
	qtdVendaProduto,
	precoVendaProduto,
	custoProduto,
	precoProduto,
	unidadeMedida,
	descontoVendaProduto,
	vendaId,
	vendaValorPago,
	vendaValorVenda,
	dataPagamento,
	localDeVenda,
	formaPagamento
) AS
	SELECT
	vi.id,
	civ.produtoId,
	civ.id,
	civ.nomeProduto,
	vi.qtd,
	vi.precoVenda,
	civ.custoProduto,
	civ.precoProduto,
	civ.unidadeMedida,
	civ.precoProduto - vi.precoVenda,
	v.id,
	v.valorPago,
	v.valorVenda,
	v.dataPagamento,
	vl.apelido,
	fp.nome
	FROM VendaProduto AS vi
	INNER JOIN CaixaProdutoView AS civ ON Civ.id = vi.caixaProdutoId
	INNER JOIN Venda AS v ON v.id = vi.vendaId
		INNER JOIN VendaLocal AS vl ON Vl.id = v.vendaLocalId
		INNER JOIN FormaPagamento AS fp ON Fp.id = v.formaPagamentoId;


-- VIEWS DE ULTIMOS

CREATE VIEW IF NOT EXISTS ProdutoAtual 
AS
	SELECT iv.* FROM ProdutoView AS iv
	WHERE iv.atual = 1;

CREATE VIEW IF NOT EXISTS CaixaProdutoAtual 
AS
	SELECT * FROM CaixaProdutoView AS civ
	WHERE civ.atual = 1;

CREATE VIEW IF NOT EXISTS CaixaProdutoAberto
AS
	SELECT cia.* FROM CaixaProdutoAtual AS cia
	INNER JOIN Caixa AS c ON c.id = cia.caixaId
	WHERE c.dataFechamento IS NULL;


-- INSERTS BASICOS

INSERT INTO UnidadeMedida (nome)
	VALUES	('un'),
			('kg'),
			('g'),
			('l'),
			('ml'),
			('m');

INSERT INTO FormaPagamento (nome)
	VALUES	('DINHEIRO'),
			('PIX'),
			('CARTAO_DEBITO'),
			('CARTAO_CREDITO');

