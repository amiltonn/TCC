package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Caixa;

import java.util.List;

@Dao
public interface CaixaDAO {

    @Insert
    void salvar(Caixa caixa);

    @Query("SELECT * FROM caixa")
    List<Caixa> listar();

    @Query("SELECT * FROM caixa WHERE id = :id")
    Caixa consultar(Integer id);

    @Delete
    void deletar(Caixa caixa);

    @Update
    void alterar(Caixa caixa);

}
