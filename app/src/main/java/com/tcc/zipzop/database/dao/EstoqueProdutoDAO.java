package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.EstoqueProduto;

import java.util.List;

@Dao
public interface EstoqueProdutoDAO {
    @Insert
    void salvar(EstoqueProduto estoqueProduto);

    @Query("SELECT * FROM EstoqueProduto")
    List<EstoqueProduto> listar();

    @Delete
    void deletar(EstoqueProduto estoqueProduto);

    @Update
    void alterar(EstoqueProduto estoqueProduto);
}
