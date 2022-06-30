package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.tcc.zipzop.entity.FormaPagamento;

import java.util.List;

@Dao
public interface FormaPagamentoDAO {

    @Query("SELECT * FROM FormaPagamento")
    List<FormaPagamento> listar();

    @Query("SELECT * FROM FormaPagamento WHERE id = :id")
    FormaPagamento consultar(Integer id);

}
