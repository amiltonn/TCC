package com.tcc.zipzop.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tcc.zipzop.entity.UnidadeMedida;

import java.util.List;

@Dao
public interface UnidadeMedidaDAO {

  @Query("SELECT * FROM UnidadeMedida")
  List<UnidadeMedida> listar();

  @Query("SELECT * FROM UnidadeMedida WHERE id = :id")
  UnidadeMedida consultar(Integer id);

}
