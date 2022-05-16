package com.tcc.zipzop.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Item;

@Dao
public interface ItemDAO {

    @Insert
    void salvar(Item item);

    @Query("SELECT * FROM item")
    Cursor listar();

    @Delete
    void deletar(Item item);

    @Update
    void alterar(Item item);
}
