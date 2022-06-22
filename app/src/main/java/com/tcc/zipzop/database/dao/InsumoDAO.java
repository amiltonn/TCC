package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.Insumo;

import java.util.List;

@Dao
public interface InsumoDAO {
    @Insert
    void salvar(Insumo insumo);

    @Query("SELECT * FROM Insumo")
    List<Insumo> listar();

    @Query("SELECT * FROM Insumo WHERE id = :id")
    Insumo consultar(Integer id);

    @Delete
    void deletar(Insumo insumo);

    @Update
    void alterar(Insumo insumo);
}
