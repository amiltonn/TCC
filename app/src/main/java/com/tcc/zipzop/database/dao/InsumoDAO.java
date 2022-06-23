package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Insumo;

import java.util.List;

@Dao
public interface InsumoDAO {
    @Insert
    void salvar(Insumo insumo);

    @Query("SELECT * FROM  Insumo WHERE ativo = 1 AND atual = 1")
    List<Insumo> listar();

    @Query("SELECT * FROM  Insumo WHERE id = :id AND ativo = 1 AND atual = 1")
    Insumo consultar(Integer id);

    @Query("UPDATE Insumo SET ativo = 0 WHERE id = :id")
    void deletar(Integer id);

    @Update
    void alterar(Insumo insumo);
}
