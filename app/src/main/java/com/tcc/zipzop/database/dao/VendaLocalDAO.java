package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.entity.VendaLocal;

import java.util.List;

@Dao
public interface VendaLocalDAO {
    @Insert
    void salvar(VendaLocal vendaLocal);

    @Query("SELECT * FROM VendaLocal WHERE id = :id")
    VendaLocal consultar(Integer id);

    @Delete
    void deletar(VendaLocal vendaLocal);

    @Update
    void alterar(VendaLocal vendaLocal);
}
