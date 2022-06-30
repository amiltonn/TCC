package com.tcc.zipzop.asynctask.venda.formaPagamento;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.FormaPagamentoDAO;
import com.tcc.zipzop.entity.FormaPagamento;

import java.util.List;

public class ListarFormaPagamentoTask extends AsyncTask<Void, Void, List<FormaPagamento>> {

  private final FormaPagamentoDAO dao;

  public ListarFormaPagamentoTask(FormaPagamentoDAO dao){
    this.dao = dao;
  }

  @Override
  protected List<FormaPagamento> doInBackground(Void... voids) {

    return dao.listar();
  }

}
