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
  static final Migration[] TODAS_MIGRATIONS = {MIGRATION_1_2};
}
