package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.CaixaFundo;

import java.util.List;

@Dao
public interface CaixaFundoDAO {

    @Insert
    void salvar(CaixaFundo caixaFundo);

    @Query("SELECT * FROM CaixaFundo")
    List<CaixaFundo> listar();

    @Query("SELECT * FROM CaixaFundo WHERE id = :id")
    CaixaFundo consultar(Integer id);

}
