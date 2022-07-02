package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

public class ConsultarCaixaProdutoPorProdutoIdAndDataAlteracaoMaxTask extends AsyncTask <Void, Void, CaixaProduto> {

  private final CaixaProdutoDAO dao;
  private final Integer produtoId;

  public ConsultarCaixaProdutoPorProdutoIdAndDataAlteracaoMaxTask(CaixaProdutoDAO dao, Integer produtooId){
    this.dao = dao;
    this.produtoId = produtooId;
  }


  @Override
  protected CaixaProduto doInBackground(Void... voids) {
    return dao.consultarPorProdutoIdAndDataAlteracaoMax(this.produtoId);
  }

}
