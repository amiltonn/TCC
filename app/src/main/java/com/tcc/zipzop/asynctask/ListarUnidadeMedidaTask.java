package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.UnidadeMedidaDAO;
import com.tcc.zipzop.entity.UnidadeMedida;

import java.util.List;

public class ListarUnidadeMedidaTask extends AsyncTask<Void, Void, List<UnidadeMedida>> {

  private final UnidadeMedidaDAO dao;

  public ListarUnidadeMedidaTask(
      UnidadeMedidaDAO dao
  ){
    this.dao = dao;
  }

  @Override
  protected List<UnidadeMedida> doInBackground(Void... voids) {
    return dao.listar();
  }

}
