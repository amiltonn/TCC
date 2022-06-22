package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.CaixaProduto;

import java.util.List;

@Dao
public interface CaixaProdutoDAO {

    @Insert
    void salvar(CaixaProduto caixaProduto);

    @Query("SELECT * FROM CaixaProduto")
    List<CaixaProduto> listar();

    @Query("SELECT * FROM CaixaProduto WHERE id = :id")
    CaixaProduto consultar(Integer id);

    @Delete
    void deletar(CaixaProduto caixaProduto);

    @Update
    void alterar(CaixaProduto caixaProduto);
}
