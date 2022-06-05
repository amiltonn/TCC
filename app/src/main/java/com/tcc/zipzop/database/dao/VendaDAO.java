package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

@Dao
public interface VendaDAO {

    @Insert
    void salvar(Venda venda);

    @Query("SELECT * FROM venda")
    List<Venda> listar();

    @Query("SELECT * FROM venda WHERE id = :id")
    Produto consultar(Long id);

    @Delete
    void deletar(Produto produto);

    @Update
    void alterar(Produto produto);

}
