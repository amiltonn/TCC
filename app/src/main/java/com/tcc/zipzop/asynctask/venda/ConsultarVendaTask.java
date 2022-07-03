package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Venda;

public class ConsultarVendaTask extends AsyncTask<Void, Void, Venda> {
  private final VendaDAO dao;
  private final Integer id;

  public ConsultarVendaTask(VendaDAO dao, Integer id) {
    this.dao = dao;
    this.id = id;
  }

  @Override
  protected Venda doInBackground(Void... voids) {
    return dao.consultar(this.id);
  }
}
