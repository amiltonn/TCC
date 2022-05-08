package com.tcc.zipzop.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ItemDAO getItemDAO();

}
