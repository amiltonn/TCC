package com.tcc.zipzop.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopMigrations {
  private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
      database.execSQL("DROP TRIGGER IF EXISTS ValidateProdutoIdInsertCaixaProduto");
      database.execSQL(
          "CREATE TRIGGER IF NOT EXISTS ValidateProdutoIdInsertCaixaProduto\n" +
              "\tBEFORE INSERT\n" +
              "\tON CaixaProduto\n" +
              "\tWHEN EXISTS(SELECT 1 FROM CaixaProduto AS cp INNER JOIN Caixa AS c ON c.id = cp.caixaId WHERE cp.produtoId = NEW.produtoId AND cp.ativo = 1 AND cp.atual = 1 AND c.dataFechamento IS NULL LIMIT 1)\n" +
              "\tBEGIN\n" +
              "\t\tSELECT RAISE(ROLLBACK, '\"caixaProduto\" com \"ativo\" 1 e mesmo \"produtoId\" já existe!');\n" +
              "\tEND;"
      );
    }
  };
  private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
      database.execSQL("PRAGMA foreign_keys = OFF;");
      database.execSQL("CREATE TEMP TABLE tempVenda AS SELECT * FROM Venda;");
      database.execSQL("DROP TABLE Venda;");
      database.execSQL(
          "CREATE TABLE IF NOT EXISTS Venda(\n" +
              "\tid INTEGER PRIMARY KEY NOT NULL,\n" +
              "\tvalorPago INTEGER NOT NULL,\n" +
              "\tvalorVenda INTEGER NOT NULL,\n" +
              "\taberta INTEGER NOT NULL DEFAULT 1,\n" +
              "\tdataPagamento TEXT NOT NULL DEFAULT ((datetime())),\n" +
              "\tvendaLocalId INTEGER,\n" +
              "\tformaPagamentoId INTEGER NOT NULL,\n" +
              "\tcaixaId INTEGER NOT NULL,\n" +
              "\tFOREIGN KEY (vendaLocalId) REFERENCES VendaLocal (id) ON UPDATE RESTRICT ON DELETE RESTRICT,\n" +
              "\tFOREIGN KEY (formaPagamentoId) REFERENCES FormaPagamento (id) ON UPDATE RESTRICT ON DELETE RESTRICT,\n" +
              "\tFOREIGN KEY (caixaId) REFERENCES Caixa (id) ON UPDATE RESTRICT ON DELETE RESTRICT\n" +
              ");"
      );
      database.execSQL("CREATE INDEX index_Venda_vendaLocalId ON Venda (vendaLocalId);");
      database.execSQL("CREATE INDEX index_Venda_formaPagamentoId ON Venda (formaPagamentoId);");
      database.execSQL("CREATE INDEX index_Venda_caixaId ON Venda (caixaId);");
      database.execSQL("INSERT INTO Venda SELECT * FROM tempVenda;");
      database.execSQL("DROP TABLE tempVenda;");
      database.execSQL("PRAGMA foreign_keys = ON;");
    }
  };
  private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
      database.execSQL("INSERT INTO UnidadeMedida (nome) VALUES ('cm');");
    }
  };
  static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4};
}
