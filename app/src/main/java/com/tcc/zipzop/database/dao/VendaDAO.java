package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

@Dao
public interface VendaDAO {

    @Insert
    void salvar(Venda venda);

    @Query("SELECT * FROM Venda")
    List<Venda> listar();

    @Query("SELECT * FROM Venda WHERE id = :id")
    Venda consultar(Long id);

}
