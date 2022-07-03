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

    @Query("SELECT v.* FROM Venda AS v INNER JOIN Caixa AS c ON c.id = v.caixaId WHERE c.dataFechamento IS NULL")
    List<Venda> listarVendaCaixaAberto();

    @Query("SELECT * FROM Venda WHERE id = :id")
    Venda consultar(Integer id);

    @Query("SELECT * FROM Venda WHERE aberta = 1 LIMIT 1")
    Venda consultarVendaAberta();

    @Query("UPDATE Venda SET aberta = 0 WHERE aberta = 1")
    void fechar();
}
