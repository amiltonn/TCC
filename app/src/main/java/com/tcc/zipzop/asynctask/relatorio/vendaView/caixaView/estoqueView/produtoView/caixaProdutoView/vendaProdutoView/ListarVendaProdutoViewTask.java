package com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView.caixaProdutoView.vendaProdutoView;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.venda.vendaProduto.ListarVendaProdutoPorVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.VendaProduto;
import com.tcc.zipzop.view.analytics.VendaProdutoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListarVendaProdutoViewTask extends AsyncTask<Void, Void, List<VendaProdutoView>> {
  private final AppCompatActivity activity;
  private final Integer vendaId;
  private final List<VendaProdutoView> vendaProdutoViewList;

  public ListarVendaProdutoViewTask(AppCompatActivity activity, Integer vendaId) {
    this.activity = activity;
    this.vendaId = vendaId;
    this.vendaProdutoViewList = new ArrayList<>();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    List<VendaProduto> vendaProdutoList = listarVendaProdutoPorVendaNaData(dataBase);
    for (VendaProduto vendaProduto : vendaProdutoList) {
      VendaProdutoView vendaProdutoView = new VendaProdutoView();
      vendaProdutoView.setVendaProduto(vendaProduto);
      vendaProdutoViewList.add(vendaProdutoView);
    }
  }

  @Override
  protected List<VendaProdutoView> doInBackground(Void... voids) {
    return this.vendaProdutoViewList;
  }

  private List<VendaProduto> listarVendaProdutoPorVendaNaData(ZipZopDataBase dataBase) {
    try {
      return new ListarVendaProdutoPorVendaTask(dataBase.getVendaProdutoDAO(), this.vendaId).execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
