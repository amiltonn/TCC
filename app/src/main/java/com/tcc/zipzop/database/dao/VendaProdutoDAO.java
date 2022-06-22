package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.List;

@Dao
public interface VendaProdutoDAO {
    @Insert
    void salvar(VendaProduto vendaProduto);

    @Query("SELECT * FROM VendaProduto")
    List<VendaProduto> listar();

    @Query("SELECT * FROM VendaProduto WHERE id = :id")
    VendaProduto consultar(Integer id);

    @Delete
    void deletar(VendaProduto vendaProduto);

    @Update
    void alterar(VendaProduto vendaProduto);
}
