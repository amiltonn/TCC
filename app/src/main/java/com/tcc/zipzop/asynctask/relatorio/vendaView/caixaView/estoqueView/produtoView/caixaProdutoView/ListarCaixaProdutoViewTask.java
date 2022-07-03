package com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView.caixaProdutoView;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListarCaixaProdutoPorCaixaNaDataTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.view.analytics.CaixaProdutoView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListarCaixaProdutoViewTask extends AsyncTask<Void, Void, List<CaixaProdutoView>> {
  private final AppCompatActivity activity;
  private final Integer caixaId;
  private final Date dataAlteracao;
  private final List<CaixaProdutoView> caixaProdutoViewList;

  public ListarCaixaProdutoViewTask(AppCompatActivity activity, Integer caixaId, Date dataAlteracao) {
    this.activity = activity;
    this.caixaId = caixaId;
    this.dataAlteracao = dataAlteracao;
    this.caixaProdutoViewList = new ArrayList<>();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    List<CaixaProduto> caixaProdutoList = listarCaixaProdutoPorCaixaNaData(dataBase);
    for (CaixaProduto caixaProduto : caixaProdutoList) {
      CaixaProdutoView caixaProdutoView = new CaixaProdutoView();
      caixaProdutoView.setCaixaProduto(caixaProduto);
      caixaProdutoViewList.add(caixaProdutoView);
    }
  }

  @Override
  protected List<CaixaProdutoView> doInBackground(Void... voids) {
    return this.caixaProdutoViewList;
  }

  private List<CaixaProduto> listarCaixaProdutoPorCaixaNaData(ZipZopDataBase dataBase) {
    try {
      return new ListarCaixaProdutoPorCaixaNaDataTask(dataBase.getCaixaProdutoDAO(), this.caixaId, this.dataAlteracao).execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
