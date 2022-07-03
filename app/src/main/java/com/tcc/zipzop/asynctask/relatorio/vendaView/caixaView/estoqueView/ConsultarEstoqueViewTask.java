package com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.estoque.ConsultarEstoqueTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView.ListarProdutoViewTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Estoque;
import com.tcc.zipzop.view.analytics.EstoqueView;
import com.tcc.zipzop.view.analytics.ProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConsultarEstoqueViewTask extends AsyncTask<Void, Void, EstoqueView> {
  private final AppCompatActivity activity;
  private final Integer id;
  private Estoque estoque;
  private List<ProdutoView> produtoViewList;
  private EstoqueView estoqueView;

  public ConsultarEstoqueViewTask(AppCompatActivity activity, Integer id) {
    this.activity = activity;
    this.id = id;
    this.estoque = new Estoque();
    this.produtoViewList = new ArrayList<>();
    this.estoqueView = new EstoqueView();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    try {
      this.estoque = new ConsultarEstoqueTask(dataBase.getEstoqueDAO(), this.id).execute().get();
      this.produtoViewList = new ListarProdutoViewTask(activity, this.id).execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected EstoqueView doInBackground(Void... voids) {
    estoqueView.setEstoque(estoque);
    estoqueView.setProdutoViewList(produtoViewList);
    return estoqueView;
  }
}
