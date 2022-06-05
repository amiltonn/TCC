package com.tcc.zipzop.database;

import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class ZipZopTriggerCreation {
//      https://proandroiddev.com/sqlite-triggers-android-room-2e7120bb3e3a
//      https://medium.com/@srinuraop/database-create-and-open-callbacks-in-room-7ca98c3286ab
    static final RoomDatabase.Callback triggerCreationCallback = new RoomDatabase.Callback() {
        public void onCreate (SupportSQLiteDatabase database) {
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS ValidateNomeInsertProduto" +
                    " BEFORE INSERT" +
                    " ON Produto" +
                    " WHEN EXISTS(SELECT 1 FROM Produto WHERE nome = NEW.nome AND atual = 1 LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, '\"produto\" com mesmo \"nome\" já existe!');" +
                    " END;"
            );
        }
    };

//      Nao sei se esta valendo:
//      [1]execSQL nao gosta de multiplas queries em um trigger
//      https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444
    static final RoomDatabase.Callback[] callbacks = {triggerCreationCallback};
}
