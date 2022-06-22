package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;

import java.util.List;

@Dao
public interface EstoqueDAO {

    @Insert
    void salvar(Estoque estoque);

    @Query("SELECT * FROM Estoque")
    List<Estoque> listar();

    @Query("SELECT * FROM Estoque WHERE id = :id")
    Estoque consultar(Integer id);

    @Delete
    void deletar(Estoque estoque);

    @Update
    void alterar(Estoque estoque);
}
