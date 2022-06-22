package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.GastoProduto;

import java.util.List;

@Dao
public interface GastoProdutoDAO {
    @Insert
    void salvar(GastoProduto gastoProduto);

    @Query("SELECT * FROM GastoProduto")
    List<GastoProduto> listar();

    @Delete
    void deletar(GastoProduto gastoProduto);

    @Update
    void alterar(GastoProduto gastoProduto);
}
