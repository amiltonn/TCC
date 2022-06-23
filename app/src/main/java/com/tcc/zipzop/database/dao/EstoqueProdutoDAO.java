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

    @Query("SELECT * FROM EstoqueProduto WHERE estoqueId = :estoqueId")
    List<EstoqueProduto> listar(Integer estoqueId);

}
