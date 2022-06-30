package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;

public class FecharVendaTask extends AsyncTask<Void, Void, Void> {
  private final VendaDAO dao;

  public FecharVendaTask(VendaDAO dao) {
    this.dao = dao;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    dao.fechar();
    return null;
  }
}
