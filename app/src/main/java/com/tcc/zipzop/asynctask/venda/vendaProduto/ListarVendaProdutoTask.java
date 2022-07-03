package com.tcc.zipzop.asynctask.venda.vendaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.List;

public class ListarVendaProdutoTask extends AsyncTask<Void, Void, List<VendaProduto>> {
  private final VendaProdutoDAO dao;

  public ListarVendaProdutoTask(VendaProdutoDAO dao) {
    this.dao = dao;
  }

  @Override
  protected List<VendaProduto> doInBackground(Void... voids) {
    return dao.listar();
  }
}