package com.tcc.zipzop.database;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopCallbacks {
//      https://proandroiddev.com/sqlite-triggers-android-room-2e7120bb3e3a
//      https://medium.com/@srinuraop/database-create-and-open-callbacks-in-room-7ca98c3286ab
    static final RoomDatabase.Callback callbacks = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase database) {
            /*Trigger for Operation*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateInsertOperationActive\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON OperationActive\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível começar uma nova operação recursiva antes de acabar uma! Tente incrementar o \"recursionLayer\" se estiver mergulhandoo em recursões.');\n" +
                    "\tEND;"
            );
            /*Triggers Produto*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Produto\n" +
                    "\tWHEN NEW.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com \"produto\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateNomeInsertProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Produto\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Produto WHERE nome = NEW.nome AND atual = 1 LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"produto\" com \"ativo\" 1 e mesmo \"nome\" já existe!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateFormulaUpdateProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Produto\n" +
                    "\tWHEN OLD.formulaId <> NEW.formulaId\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"formulaId\" de \"produto\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateAtualUpdateProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Produto\n" +
                    "\tWHEN OLD.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"produto\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteProduto\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON Produto\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"produto\" nao pode ser deletado, desative-o trocando \"ativo\" para \"0\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS InsertProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Produto\n" +
                    "\tWHEN NEW.formulaId IS NULL AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO Formula (aberta)\n" +
                    "\t\t\tVALUES (0);\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO Produto (nome, qtd, custo, preco, ativo, unidadeMedidaId, formulaId)\n" +
                    "\t\t\tVALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, NEW.ativo, NEW.unidadeMedidaId, (SELECT MAX(id) FROM Formula));\n" +
                    "\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Produto\n" +
                    "\tWHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\n" +
                    "\t\tUPDATE Produto\n" +
                    "\t\tSET atual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\n" +
                    "\t\tINSERT INTO Produto (nome, qtd, custo, preco, produtoAntesId, ativo, unidadeMedidaId, formulaId)\n" +
                    "\t\t\tVALUES (NEW.nome, NEW.qtd, NEW.custo, NEW.preco, OLD.id, NEW.ativo, NEW.unidadeMedidaId, OLD.formulaId);\n" +
                    "\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateDeleteProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Produto\n" +
                    "\tWHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\n" +
                    "\t\tUPDATE Produto\n" +
                    "\t\tSET\tativo = 0,\n" +
                    "\t\t\tatual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            /*Triggers CaixaProduto*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertCaixaProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Produto\n" +
                    "\tWHEN NEW.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com \"caixaProduto\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateProdutoIdInsertCaixaProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM CaixaProduto WHERE produtoId = NEW.produtoId AND atual = 1 LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"caixaProduto\" com \"ativo\" 1 e mesmo \"produtoId\" já existe!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateCaixaFundoInsertCaixaProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN NOT EXISTS(SELECT 1 FROM CaixaFundo WHERE caixaId = NEW.caixaId LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Antes de inserir um \"caixaProduto\" é necessário inserir um \"caixaFundo\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixaProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN OLD.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"caixaProduto\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixaProduto\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON CaixaProduto\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"caixaProduto\" nao pode ser deletado, desative-o trocando \"ativo\" para \"0\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS AfterInsertCaixaProduto\n" +
                    "\tAFTER INSERT\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM CaixaFundo WHERE caixaId = NEW.caixaId LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO caixaFundo (valor, caixaId)\n" +
                    "\t\t\tSELECT valor, caixaId FROM CaixaFundo AS cf\n" +
                    "\t\t\t\tWHERE cf.dataAlteracao = (SELECT MAX(dataAlteracao) FROM CaixaFundo WHERE caixaId = NEW.caixaId) LIMIT 1;\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateCaixaProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\n" +
                    "\t\tUPDATE CaixaProduto\n" +
                    "\t\tSET atual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO CaixaProduto (qtd, ativo, produtoId, caixaId)\n" +
                    "\t\t\tVALUES (NEW.qtd, NEW.ativo, OLD.produtoId, OLD.caixaId);\n" +
                    "\t\t\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateDeleteCaixaProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\n" +
                    "\t\tUPDATE CaixaProduto\n" +
                    "\t\tSET\tativo = 0,\n" +
                    "\t\t\tatual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            /*Triggers CaixaFundo*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDuplicadoInsertCaixaFundo\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON CaixaFundo\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM CaixaFundo WHERE dataAlteracao = NEW.dataAlteracao LIMIT 1)\n" +
                    "\tBEGIN \n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixaFundo\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON CaixaFundo\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"caixaFundo\" nao pode ser atualizado, insira outro caixa fundo relacionado ao mesmo caixa!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixaFundo\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON CaixaFundo\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"caixaFundo\" nao pode ser deletado!');\n" +
                    "\tEND;"
            );
            /*Triggers associados a Caixa*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Produto\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"produto\" com um \"caixa\" aberto!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertCaixaProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON CaixaProduto\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Caixa WHERE NEW.caixaId = id AND dataFechamento IS NOT NULL LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"caixaProduto\" com um \"caixa\" fechado!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateCaixaInsertCaixaFundo\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON CaixaFundo\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Caixa WHERE NEW.caixaId = id AND dataFechamento IS NOT NULL LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT ou UPDATE em \"caixaFundo\" com um \"caixa\" fechado!');\n" +
                    "\tEND;"
            );
            /*Triggers Caixa*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateAbertoInsertCaixa\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Caixa\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Ainda há um \"caixa\" aberto!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateCaixa\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Caixa\n" +
                    "\tWHEN\tOLD.id <> NEW.id\n" +
                    "\t\tOR\tOLD.dataAbertura <> NEW.dataAbertura\n" +
                    "\t\tOR\tOLD.estoqueId <> NEW.estoqueId\n" +
                    "\t\tOR\tOLD.dataFechamento IS NOT NULL\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'UPDATE em \"caixa\" desautorizado! Você tentou mudar \"id\", \"dataAbertura\", \"estoqueId\" ou alterar um \"caixa\" já fechado.');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteCaixa\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON Caixa\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"caixa\" nao pode ser deletado!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS InsertCaixa\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Caixa\n" +
                    "\tWHEN NOT EXISTS(SELECT 1 FROM Caixa WHERE dataFechamento IS NULL LIMIT 1) AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO Estoque (dataAlteracao) VALUES (datetime());\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO EstoqueProduto (estoqueId, produtoId)\n" +
                    "\t\t\tSELECT e.id, i.id\n" +
                    "\t\t\t\tFROM Estoque AS e, produto AS i\n" +
                    "\t\t\t\tWHERE\n" +
                    "\t\t\t\t\te.dataAlteracao = (SELECT MAX(dataAlteracao) FROM Estoque)\n" +
                    "\t\t\t\t\t\tAND\n" +
                    "\t\t\t\t\ti.atual = 1;\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\t\t\n" +
                    "\t\tINSERT INTO Caixa (estoqueId)\n" +
                    "\t\t\tSELECT e.id\n" +
                    "\t\t\t\tFROM Estoque AS e\n" +
                    "\t\t\t\tWHERE\n" +
                    "\t\t\t\t\te.dataAlteracao =  (SELECT MAX(dataAlteracao) FROM Estoque);\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            /*Triggers VendaProduto*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateInsertVendaProduto\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON VendaProduto\n" +
                    "\tWHEN (SELECT aberta FROM Venda WHERE id = NEW.vendaId) = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT em \"vendaProduto\" de venda executada!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateVendaProduto\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON VendaProduto\n" +
                    "\tWHEN OLD.vendaId IS NOT NULL OR (SELECT aberta FROM Venda WHERE id = OLD.vendaId) = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"vendaProduto\" de venda executada!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteVendaProduto\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON VendaProduto\n" +
                    "\tWHEN OLD.vendaId IS NOT NULL OR (SELECT aberta FROM Venda WHERE id = OLD.vendaId) = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em \"vendaProduto\" de venda executada!');\n" +
                    "\tEND;"
            );
            /*Triggers Venda*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateVenda\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Venda\n" +
                    "\tWHEN OLD.aberta = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"venda\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteVenda\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON Venda\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar DELETE em \"venda\"!');\n" +
                    "\tEND;"
            );
            /*Triggers Insumo*/
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateAtualInsertInsumo\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Insumo\n" +
                    "\tWHEN NEW.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar INSERT com \"insumo\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateFormulaInsumoIdsInsertInsumo\n" +
                    "\tBEFORE INSERT\n" +
                    "\tON Insumo\n" +
                    "\tWHEN EXISTS(SELECT 1 FROM Insumo WHERE formulaId = NEW.formulaId AND insumoId = NEW.insumoId AND atual = 1 LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"insumo\" com \"ativo\" 1 e mesmos \"formulaId\" e \"insumoId\" já existe!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateUpdateInsumo\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Insumo\n" +
                    "\tWHEN OLD.atual = 0\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, 'Não é possível dar UPDATE em \"insumo\" não \"atual\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateDeleteInsumo\n" +
                    "\tBEFORE DELETE\n" +
                    "\tON Insumo\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT RAISE(ROLLBACK, '\"insumo\" nao pode ser deletado, desative-o trocando \"ativo\" para \"0\"!');\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateInsumo\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Insumo\n" +
                    "\tWHEN NEW.ativo <> 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\n" +
                    "\t\tUPDATE Insumo\n" +
                    "\t\tSET atual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\n" +
                    "\t\tINSERT INTO Insumo (qtdInsumoProduto, ativo, formulaId, insumoId)\n" +
                    "\t\t\tVALUES (NEW.qtdInsumoProduto, NEW.ativo, OLD.formulaId, OLD.insumoId);\n" +
                    "\t\t\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS UpdateDeleteInsumo\n" +
                    "\tBEFORE UPDATE\n" +
                    "\tON Insumo\n" +
                    "\tWHEN NEW.ativo = 0 AND OLD.atual = 1 AND NOT EXISTS(SELECT 1 FROM OperationActive LIMIT 1)\n" +
                    "\tBEGIN\n" +
                    "\t\tINSERT INTO OperationActive (recursionLayer)\n" +
                    "\t\t\tVALUES (1);\n" +
                    "\t\t\n" +
                    "\t\tUPDATE Insumo\n" +
                    "\t\tSET\tativo = 0,\n" +
                    "\t\t\tatual = 0\n" +
                    "\t\tWHERE id = OLD.id;\n" +
                    "\t\n" +
                    "\t\tDELETE FROM OperationActive;\n" +
                    "\t\t\t\t\n" +
                    "\t\tSELECT RAISE(IGNORE);\n" +
                    "\tEND;"
            );
            /*Inserts Basicos*/
            database.execSQL(
                "INSERT INTO UnidadeMedida (nome)\n" +
                    "\tVALUES\t('un'),\n" +
                    "\t\t\t('kg'),\n" +
                    "\t\t\t('g'),\n" +
                    "\t\t\t('l'),\n" +
                    "\t\t\t('ml'),\n" +
                    "\t\t\t('m');"
            );
            database.execSQL(
                "INSERT INTO FormaPagamento (nome)\n" +
                    "\tVALUES\t('DINHEIRO'),\n" +
                    "\t\t\t('PIX'),\n" +
                    "\t\t\t('CARTAO_DEBITO'),\n" +
                    "\t\t\t('CARTAO_CREDITO');"
            );
        }
    };
}
//      Nao sei se esta valendo:
//      [1]execSQL nao gosta de multiplas queries em um trigger
//      https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444
