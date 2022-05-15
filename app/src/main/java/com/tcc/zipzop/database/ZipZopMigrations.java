package com.tcc.zipzop.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopMigrations {
    private static final Migration MIGRATIONS = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            /*CREATES*/
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS unidade_medida(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "nome TEXT NOT NULL UNIQUE" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS forma_pagamento(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "nome TEXT NOT NULL UNIQUE" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS estoque(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "data_alteracao DATETIME NOT NULL UNIQUE DEFAULT (datetime())" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS item(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "qtd INTEGER NOT NULL," +
                    "custo NUMERIC NOT NULL," +
                    "preco NUMERIC," +
                    "ativo BOOLEAN NOT NULL DEFAULT (true) CHECK (ativo IN (false, true))," +
                    "atual BOOLEAN NOT NULL DEFAULT (true) CHECK (atual IN (false, true))," +
                    "data_alteracao DATETIME NOT NULL DEFAULT (datetime())," +
                    "item_antes_id INTEGER DEFAULT NULL," +
                    "unidade_medida_id INTEGER NOT NULL," +
                    "formula_id INTEGER DEFAULT NULL," +
                    "FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (formula_id) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS estoque_item(" +
                    "estoque_id INTEGER NOT NULL," +
                    "item_id INTEGER NOT NULL," +
                    "PRIMARY KEY (estoque_id, item_id)," +
                    "FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ") WITHOUT ROWID;"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS caixa(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "data_abertura DATETIME NOT NULL DEFAULT (datetime())," +
                    "data_fechamento DATETIME," +
                    "estoque_id INTEGER NOT NULL," +
                    "FOREIGN KEY (estoque_id) REFERENCES estoque (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS caixa_fundo(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "valor NUMERIC NOT NULL," +
                    "data_alteracao DATETIME NOT NULL DEFAULT (datetime())," +
                    "caixa_id INTEGER NOT NULL," +
                    "FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS caixa_item(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "qtd INTEGER NOT NULL," +
                    "data_alteracao DATETIME NOT NULL DEFAULT (datetime())," +
                    "item_id INTEGER NOT NULL," +
                    "caixa_id INTEGER NOT NULL," +
                    "FOREIGN KEY (item_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS venda(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "valor_pago NUMERIC NOT NULL," +
                    "valor_venda NUMERIC NOT NULL," +
                    "data_pagamento DATETIME NOT NULL DEFAULT (datetime())," +
                    "forma_pagamento_id INTEGER NOT NULL," +
                    "caixa_id INTEGER NOT NULL," +
                    "FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (caixa_id) REFERENCES caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS venda_item(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "qtd INTEGER NOT NULL," +
                    "preco_venda NUMERIC," +
                    "venda_id INTEGER NOT NULL," +
                    "caixa_item_id INTEGER NOT NULL," +
                    "FOREIGN KEY (venda_id) REFERENCES venda (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (caixa_item_id) REFERENCES caixa_item (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS formula(" +
                    "id INTEGER PRIMARY KEY NOT NULL" +
                ");"
            );
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS insumo(" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "qtd_insumo_item INTEGER NOT NULL," +
                    "ativo BOOLEAN NOT NULL DEFAULT (true) CHECK (ativo IN (false, true))," +
                    "data_alteracao DATETIME NOT NULL DEFAULT (datetime())," +
                    "formula_id INTEGER NOT NULL," +
                    "insumo_id INTEGER NOT NULL," +
                    "FOREIGN KEY (formula_id) REFERENCES formula (id) ON UPDATE RESTRICT ON DELETE RESTRICT," +
                    "FOREIGN KEY (insumo_id) REFERENCES item (id) ON UPDATE RESTRICT ON DELETE RESTRICT" +
                ");"
            );
            /*TRIGGERS ITEM*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_atual_insert_item" +
                    " BEFORE INSERT" +
                    " ON item" +
                    " WHEN NEW.atual = false" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em \"item\" não \"atual\"!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_nome_insert_item" +
                    " BEFORE INSERT" +
                    " ON item" +
                    " WHEN EXISTS(SELECT 1 FROM item WHERE nome = NEW.nome AND NEW.atual = true LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, '\"item\" com mesmo \"nome\" já existe!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_atual_update_item" +
                    " BEFORE UPDATE" +
                    " ON item" +
                    " WHEN OLD.atual = false" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"item\" não \"atual\"!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_delete_item" +
                    " BEFORE DELETE" +
                    " ON item" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, '\"item\" nao pode ser deletado, desative-o trocando \"ativo\" para \"false\"!');" +
                    " END;"
            );
            /*execSQL nao gosta de multiplas queries em um trigger
            CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS update_item3" +
                    " BEFORE UPDATE" +
                    " ON item" +
                    " WHEN OLD.atual = true" +
                    " BEGIN" +
                    " 	SELECT RAISE(IGNORE);" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS update_item2" +
                    " BEFORE UPDATE" +
                    " ON item" +
                    " WHEN OLD.atual = true" +
                    " BEGIN" +
                    " 	INSERT INTO item (nome, qtd, custo, preco, item_antes_id, ativo, unidade_medida_id)" +
                    " 		VALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, OLD.id, NEW.ativo, NEW.unidade_medida_id);" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS update_item1" +
                    " BEFORE UPDATE" +
                    " ON item" +
                    " WHEN OLD.atual = true" +
                    " BEGIN" +
                    " 	UPDATE item" +
                    " 	SET atual = false" +
                    " 	WHERE id = OLD.id;" +
                    " END;"
            );
            /*CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            /*TRIGGERS CAIXA_ITEM*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_caixa_fundo_insert_caixa_item" +
                    " BEFORE INSERT" +
                    " ON caixa_item" +
                    " WHEN NOT EXISTS(SELECT 1 FROM caixa_fundo WHERE caixa_id = NEW.caixa_id LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Antes de inserir um \"caixa_item\" é necessário inserir um \"caixa_fundo\"!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_update_caixa_item" +
                    " BEFORE UPDATE" +
                    " ON caixa_item" +
                    " WHEN OLD.id IN (SELECT id FROM caixa_item WHERE data_alteracao < (SELECT MAX(data_alteracao) FROM caixa_item WHERE item_id = OLD.item_id))" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em \"caixa_item\" não \"atual\"!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_delete_caixa_item" +
                    " BEFORE DELETE" +
                    " ON caixa_item" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, '\"caixa_item\" nao pode ser deletado, desative-o trocando \"ativo\" para \"false\"!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS after_insert_caixa_item" +
                    " AFTER INSERT" +
                    " ON caixa_item" +
                    " WHEN EXISTS(SELECT 1 FROM caixa_fundo WHERE caixa_id = NEW.caixa_id LIMIT 1)" +
                    " BEGIN" +
                    " 	INSERT INTO caixa_fundo (valor, caixa_id)" +
                    " 		SELECT valor, caixa_id FROM caixa_fundo AS cf" +
                    " 			WHERE cf.data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_fundo WHERE caixa_id = NEW.caixa_id) LIMIT 1;" +
                    " END;"
            );
            /*execSQL nao gosta de multiplas queries em um trigger
            CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS update_caixa_item2" +
                    " BEFORE UPDATE" +
                    " ON caixa_item" +
                    " WHEN OLD.id IN (SELECT id FROM caixa_item WHERE data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_item WHERE item_id = OLD.item_id))" +
                    " BEGIN" +
                    " 	SELECT RAISE(IGNORE);" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS update_caixa_item1" +
                    " BEFORE UPDATE" +
                    " ON caixa_item" +
                    " WHEN OLD.id IN (SELECT id FROM caixa_item WHERE data_alteracao = (SELECT MAX(data_alteracao) FROM caixa_item WHERE item_id = OLD.item_id))" +
                    " BEGIN" +
                    " 	INSERT INTO caixa_item (qtd, item_id, caixa_id)" +
                    " 		VALUES (NEW.qtd, OLD.item_id, OLD.caixa_id);" +
                    " END;"
            );
            /*CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            /*TRIGGER CAIXA_FUNDO*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_duplicado_insert_caixa_fundo" +
                    " BEFORE INSERT" +
                    " ON caixa_fundo" +
                    " WHEN EXISTS(SELECT 1 FROM caixa_fundo WHERE data_alteracao = NEW.data_alteracao LIMIT 1)" +
                    " BEGIN " +
                    " 	SELECT RAISE(IGNORE);" +
                    " END;"
            );
            /*TRIGGER ASSOCIADOS A CAIXA*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_item" +
                    " BEFORE INSERT" +
                    " ON item" +
                    " WHEN EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"item\" com um \"caixa\" aberto!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_caixa_item" +
                    " BEFORE INSERT" +
                    " ON caixa_item" +
                    " WHEN EXISTS(SELECT 1 FROM caixa WHERE NEW.caixa_id = id AND data_fechamento IS NOT NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"caixa_item\" com um \"caixa\" fechado!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_caixa_insert_caixa_fundo" +
                    " BEFORE INSERT" +
                    " ON caixa_fundo" +
                    " WHEN EXISTS(SELECT 1 FROM caixa WHERE NEW.caixa_id = id AND data_fechamento IS NOT NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"caixa_fundo\" com um \"caixa\" fechado!');" +
                    " END;"
            );
            /*TRIGGER CAIXA*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_aberto_insert_caixa" +
                    " BEFORE INSERT" +
                    " ON caixa" +
                    " WHEN EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'Ainda há um \"caixa\" aberto!');" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_update_caixa" +
                    " BEFORE UPDATE" +
                    " ON caixa" +
                    " WHEN OLD.id <> NEW.id" +
                    " 	OR OLD.data_abertura <> NEW.data_abertura" +
                    " 	OR OLD.estoque_id <> NEW.estoque_id" +
                    " 	OR OLD.data_fechamento IS NOT NULL" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, 'UPDATE em \"caixa\" desautorizado! Você tentou mudar \"id\", \"data_abertura\", \"estoque_id\" ou alterar um \"caixa\" já fechado.');" +
                    " END;"
            );
            /*execSQL nao gosta de multiplas queries em um trigger
            CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS insert_caixa4" +
                    " BEFORE INSERT" +
                    " ON caixa" +
                    " WHEN NOT EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(IGNORE);" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS insert_caixa3" +
                    " BEFORE INSERT" +
                    " ON caixa" +
                    " WHEN NOT EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	INSERT INTO caixa (estoque_id)" +
                    " 		SELECT e.id" +
                    " 			FROM estoque AS e" +
                    " 			WHERE" +
                    " 				e.data_alteracao =  (SELECT MAX(data_alteracao) FROM estoque);" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS insert_caixa2" +
                    " BEFORE INSERT" +
                    " ON caixa" +
                    " WHEN NOT EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	INSERT INTO estoque_item (estoque_id, item_id)" +
                    " 		SELECT e.id, i.id" +
                    " 			FROM estoque AS e, item AS i" +
                    " 			WHERE" +
                    " 				e.data_alteracao = (SELECT MAX(data_alteracao) FROM estoque)" +
                    " 					AND" +
                    " 				i.atual = true;" +
                    " END;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS insert_caixa1" +
                    " BEFORE INSERT" +
                    " ON caixa" +
                    " WHEN NOT EXISTS(SELECT 1 FROM caixa WHERE data_fechamento IS NULL LIMIT 1)" +
                    " BEGIN" +
                    " 	INSERT INTO estoque (data_alteracao) VALUES (datetime());" +
                    " END;"
            );
            /*CREATED FIRST EXECUTED LAST https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444*/
            /*INSERTS BASICOS*/
            database.execSQL(
                "INSERT INTO unidade_medida (nome)" +
                    " VALUES ('UNIDADES')," +
                    " 		('KILOS')," +
                    " 		('GRAMAS')," +
                    " 		('LITROS')," +
                    " 		('MILILITROS')," +
                    " 		('METROS')," +
                    " 		('PUNHADO')," +
                    " 		('LAPADA');"
            );
            database.execSQL(
                "INSERT INTO forma_pagamento (nome)" +
                    " VALUES ('DINHEIRO')," +
                    " 		('PIX')," +
                    " 		('CARTAO_DEBITO')," +
                    " 		('CARTAO_CREDITO');"
            );
        }
    };


    static final Migration[] TODAS_MIGRATIONS = {MIGRATIONS};

}
