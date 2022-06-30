package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Venda;

public class ConsultarVendaAbertaTask extends AsyncTask<Void, Void, Venda> {
  private final VendaDAO dao;

  public ConsultarVendaAbertaTask(VendaDAO dao) {
    this.dao = dao;
  }

  @Override
  protected Venda doInBackground(Void... voids) {
    return dao.vendaAberta();
  }
}
