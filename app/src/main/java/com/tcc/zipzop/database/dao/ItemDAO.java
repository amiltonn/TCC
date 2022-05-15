package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Item;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    void salvar(Item item);

    @Query("SELECT * FROM item")
    List<Item> listar();

    @Delete
    void deletar(Item item);

    @Update
    void alterar(Item item);
}
