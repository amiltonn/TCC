package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tcc.zipzop.entity.Caixa;

import java.util.List;

@Dao
public interface CaixaDAO {

    @Query("INSERT INTO Caixa DEFAULT VALUES")
    void salvar();

    @Query("SELECT * FROM Caixa")
    List<Caixa> listar();

    @Query("SELECT * FROM Caixa WHERE id = :id")
    Caixa consultar(Integer id);

    @Query("UPDATE Caixa SET dataFechamento = (datetime())")
    void fechar();

}
