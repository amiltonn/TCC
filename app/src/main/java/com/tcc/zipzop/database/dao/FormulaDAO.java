package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.Formula;

import java.util.List;

@Dao
public interface FormulaDAO {

    @Insert
    void salvar(Formula formula);

    @Query("SELECT * FROM Formula")
    List<Formula> listar();

    @Query("SELECT * FROM Formula WHERE id = :id")
    Formula consultar(Integer id);

    @Delete
    void deletar(Formula formula);

    @Update
    void alterar(Formula formula);
}
