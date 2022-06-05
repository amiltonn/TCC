package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.Item;

@Database(entities = {Item.class, Caixa.class}, version = 1, exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ItemDAO getItemDAO();
    public abstract CaixaDAO getCaixaDAO();

    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(ZipZopTriggerCreation.triggerCreationCallback)
                .build();
    }
}
