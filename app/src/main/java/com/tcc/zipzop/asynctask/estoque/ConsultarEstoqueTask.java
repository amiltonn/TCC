package com.tcc.zipzop.asynctask.estoque;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.EstoqueDAO;
import com.tcc.zipzop.entity.Estoque;

public class ConsultarEstoqueTask extends AsyncTask<Void, Void, Estoque> {
  private final EstoqueDAO dao;
  private final Integer id;

  public ConsultarEstoqueTask(EstoqueDAO dao, Integer id) {
    this.dao = dao;
    this.id = id;
  }

  @Override
  protected Estoque doInBackground(Void... voids) {
    return dao.consultar(this.id);
  }
}
