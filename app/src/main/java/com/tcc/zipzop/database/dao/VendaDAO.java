package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Item;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

@Dao
public interface VendaDAO {

    @Insert
    void salvar(Venda venda);

    @Query("SELECT * FROM venda")
    List<Venda> listar();

    @Query("SELECT * FROM venda WHERE id = :id")
    Item consultar(Long id);

    @Delete
    void deletar(Item item);

    @Update
    void alterar(Item item);

}
