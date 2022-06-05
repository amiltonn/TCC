package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.Produto;

@Database(entities = {Produto.class, Caixa.class}, version = 1, exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ProdutoDAO getProdutoDAO();
    public abstract CaixaDAO getCaixaDAO();

    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(ZipZopTriggerCreation.triggerCreationCallback)
                .build();
    }
}
