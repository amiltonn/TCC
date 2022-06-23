package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.UnidadeMedida;

import java.util.List;

@Dao
public interface FormaPagamentoDAO {

    @Query("SELECT * FROM FormaPagamento")
    List<UnidadeMedida> listar();

    @Query("SELECT * FROM FormaPagamento WHERE id = :id")
    UnidadeMedida consultar(Integer id);

}
