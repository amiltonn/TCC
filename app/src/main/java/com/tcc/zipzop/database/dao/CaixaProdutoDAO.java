package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.CaixaProduto;

import java.util.Date;
import java.util.List;

@Dao
public interface CaixaProdutoDAO {

    @Insert
    void salvar(CaixaProduto caixaProduto);

    @Query("SELECT * FROM CaixaProduto WHERE ativo = 1 AND atual = 1")
    List<CaixaProduto> listar();

    @Query("SELECT cp.* FROM CaixaProduto AS cp " +
        "INNER JOIN CaixaFundo AS cf ON cf.caixaId = :caixaId AND cf.dataAlteracao >= cp.dataAlteracao AND cf.dataAlteracao < datetime(cp.dataAlteracao, '+3 seconds') " +
        "WHERE cp.caixaId = :caixaId AND cf.dataAlteracao = :dataAlteracao")
    List<CaixaProduto> listarCaixaProdutoPorCaixaNaData(Integer caixaId, Date dataAlteracao);

    @Query("SELECT * FROM CaixaProduto WHERE id = :id AND ativo = 1 AND atual = 1")
    CaixaProduto consultar(Integer id);

    @Query("SELECT cp.* FROM CaixaProduto AS cp " +
        "INNER JOIN CaixaFundo AS cf ON cf.caixaId = cp.caixaId " +
        "WHERE cp.produtoId = :produtoId  AND cp.ativo = 1 AND cp.atual = 1 AND cf.dataAlteracao = (SELECT MAX(dataAlteracao) FROM CaixaFundo WHERE caixaId = cp.caixaId)")
    CaixaProduto consultarPorProdutoIdAndDataAlteracaoMax(Integer produtoId);

    @Query("UPDATE CaixaProduto SET ativo = 0 WHERE id = :id")
    void deletar(Integer id);

    @Query("SELECT * FROM CaixaProduto WHERE caixaId = :caixaId AND ativo = 1 AND atual = 1")
    List<CaixaProduto> listarCaixaProdutoAberto(Integer caixaId);

    @Update
    void alterar(CaixaProduto caixaProduto);

}
