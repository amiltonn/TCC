package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.EstoqueProduto;
import com.tcc.zipzop.entity.Produto;

import java.util.List;

@Dao
public interface EstoqueProdutoDAO {

    @Query("SELECT p.* FROM Produto AS p " +
        "INNER JOIN EstoqueProduto AS ep ON ep.produtoId = p.id " +
        "WHERE ep.estoqueId = :estoqueId")
    List<Produto> listarProdutoPorEstoque(Integer estoqueId);

}
