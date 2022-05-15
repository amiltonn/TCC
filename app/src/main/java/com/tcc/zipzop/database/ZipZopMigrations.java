package com.tcc.zipzop.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopMigrations {

    private static final Migration MIGRATION_UNICA = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS item(" +
                    "tid INTEGER PRIMARY KEY NOT NULL," +
                    "tnome TEXT NOT NULL," +
                    "tqtd INTEGER NOT NULL," +
                    "tcusto NUMERIC NOT NULL," +
                    "tpreco NUMERIC," +
                    "tativo BOOLEAN NOT NULL DEFAULT true CHECK (ativo IN (false, true))," +
                    "tatual BOOLEAN NOT NULL DEFAULT true CHECK (atual IN (false, true))," +
                    "tdata_alteracao DATETIME NOT NULL DEFAULT (datetime())," +
                    "titem_antes_id INTEGER DEFAULT NULL," +
                    "tunidade_medida_id INTEGER NOT NULL");
        }
    };


    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_UNICA};

}
