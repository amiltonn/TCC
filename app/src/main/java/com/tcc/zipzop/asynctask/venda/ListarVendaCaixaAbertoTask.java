package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

public class ListarVendaCaixaAbertoTask  extends AsyncTask<Void, Void, List<Venda>> {
  private final VendaDAO dao;

  public ListarVendaCaixaAbertoTask(
      VendaDAO dao
  ){
    this.dao = dao;
  }

  @Override
  protected List<Venda> doInBackground(Void... voids) {
    return dao.listarVendaCaixaAberto();
  }
}
