package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

public class ConsultarCaixaProdutoTask extends AsyncTask <Void, Void, CaixaProduto> {

  private final CaixaProdutoDAO dao;
  private final Integer id;

  public ConsultarCaixaProdutoTask(
      CaixaProdutoDAO dao,
      Integer id
  ){
    this.dao = dao;
    this.id = id;
  }


  @Override
  protected CaixaProduto doInBackground(Void... voids) {
    return dao.consultar(this.id);
  }

}
