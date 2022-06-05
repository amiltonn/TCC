package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {

    @Insert
    void salvar(Produto produto);

    @Query("SELECT * FROM Produto WHERE ativo = 1")
    List<Produto> listar();

    @Query("SELECT * FROM Produto WHERE id = :id AND ativo = 1")
    Produto consultar(Integer id);

    @Query("UPDATE Produto SET ativo = 0 WHERE id = :id")
    void deletar(Integer id);

    @Update
    void alterar(Produto produto);
}
