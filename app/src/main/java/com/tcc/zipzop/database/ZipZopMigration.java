package com.tcc.zipzop.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopMigration {
    private static final Migration MIGRATIONS = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                ""
            );
        }
    };

//  Nao sei se esta valendo:
//  [1]execSQL nao gosta de multiplas queries em um trigger https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444

    static final Migration[] TODAS_MIGRATIONS = {MIGRATIONS};

}
