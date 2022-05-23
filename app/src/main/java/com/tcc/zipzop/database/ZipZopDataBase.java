package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ItemDAO getItemDAO();

//      https://proandroiddev.com/sqlite-triggers-android-room-2e7120bb3e3a
//      https://medium.com/@srinuraop/database-create-and-open-callbacks-in-room-7ca98c3286ab
    public static RoomDatabase.Callback roomObliteratorCallback = new RoomDatabase.Callback() {
        public void onCreate (SupportSQLiteDatabase database) {
            database.execSQL(
                "CREATE TRIGGER IF NOT EXISTS validate_nome_insert_item" +
                    " BEFORE INSERT" +
                    " ON item" +
                    " WHEN EXISTS(SELECT 1 FROM item WHERE nome = NEW.nome AND atual = 1" +
                    " LIMIT 1)" +
                    " BEGIN" +
                    " 	SELECT RAISE(ROLLBACK, '\"item\" com mesmo \"nome\" já existe!');" +
                    " END;"
            );
        }
    };

//      Nao sei se esta valendo:
//      [1]execSQL nao gosta de multiplas queries em um trigger
//      https://stackoverflow.com/questions/68101791/sqlite-room-trigger-two-queries#comment120372984_68104444

    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(roomObliteratorCallback)
                .build();
    }
}
