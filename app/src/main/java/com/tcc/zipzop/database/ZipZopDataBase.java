package com.tcc.zipzop.database;

import static com.tcc.zipzop.database.ZipZopMigrations.TODAS_MIGRATIONS;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;
import com.tcc.zipzop.entity.UnidadeMedida;

@Database(entities = {Item.class, UnidadeMedida.class}, version = 1, exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ItemDAO getItemDAO();



    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .allowMainThreadQueries()
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }

}
