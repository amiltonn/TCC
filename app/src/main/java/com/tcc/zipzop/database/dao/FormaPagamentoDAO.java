package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.FormaPagamento;

import java.util.List;

@Dao
public interface FormaPagamentoDAO {
    @Insert
    void salvar(FormaPagamento formaPagamento);

    @Query("SELECT * FROM FormaPagamento")
    List<FormaPagamento> listar();

    @Query("SELECT * FROM FormaPagamento WHERE id = :id")
    FormaPagamento consultar(Integer id);

    @Delete
    void deletar(FormaPagamento formaPagamento);

    @Update
    void alterar(FormaPagamento formaPagamento);
}
