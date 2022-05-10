package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.tcc.zipzop.entity.Item;

@Dao
public interface ItemDAO {

    @Insert
    void salvar(Item item);
}
