package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

public class EditarCaixaProdutoTask extends AsyncTask<Void, Void, Void> {

  private final CaixaProdutoDAO dao;
  private final CaixaProduto caixaproduto;

  public EditarCaixaProdutoTask(CaixaProdutoDAO dao, CaixaProduto caixaproduto){
    this.dao = dao;
    this.caixaproduto = caixaproduto;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    dao.alterar(caixaproduto);
    return null;
  }
}
