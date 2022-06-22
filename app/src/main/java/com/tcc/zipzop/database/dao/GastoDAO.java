package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.Gasto;

import java.util.List;

@Dao
public interface GastoDAO {

    @Insert
    void salvar(Gasto gasto);

    @Query("SELECT * FROM Gasto")
    List<Gasto> listar();

    @Query("SELECT * FROM Gasto WHERE id = :id")
    Gasto consultar(Integer id);

    @Delete
    void deletar(Gasto gasto);

    @Update
    void alterar(Gasto gasto);
}
