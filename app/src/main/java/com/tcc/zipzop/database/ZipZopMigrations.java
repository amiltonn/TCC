package com.tcc.zipzop.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopMigrations {

    private static final Migration MIGRATION_UNICA = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("");
        }
    };


    static final Migration[] TODAS_MIGRATIONS = {MIGRATION_UNICA};

}
