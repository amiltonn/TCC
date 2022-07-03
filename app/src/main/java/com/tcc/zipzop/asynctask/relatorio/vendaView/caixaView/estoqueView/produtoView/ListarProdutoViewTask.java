package com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.estoque.ListarProdutoPorEstoqueTask;
import com.tcc.zipzop.asynctask.produto.unidadeMedida.ConsultarUnidadeMedidaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.UnidadeMedida;
import com.tcc.zipzop.view.analytics.ProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListarProdutoViewTask extends AsyncTask<Void, Void, List<ProdutoView>> {
  private final AppCompatActivity activity;
  private final Integer estoqueId;
  private final List<ProdutoView> produtoViewList;

  public ListarProdutoViewTask(AppCompatActivity activity, Integer estoqueId) {
    this.activity = activity;
    this.estoqueId = estoqueId;
    this.produtoViewList = new ArrayList<>();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    List<Produto> produtoList = listarProdutoPorEstoque(dataBase);
    for (Produto produto : produtoList) {
      ProdutoView produtoView = new ProdutoView();
      try {
        UnidadeMedida unidadeMedida = new ConsultarUnidadeMedidaTask(dataBase.getUnidadeMedidaDAO(), produto.getUnidadeMedidaId()).execute().get();
      } catch (ExecutionException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      produtoView.setProduto(produto);
      produtoView.setUnidadeMedida(produtoView.getUnidadeMedida());
      produtoViewList.add(produtoView);
    }
  }

  @Override
  protected List<ProdutoView> doInBackground(Void... voids) {
    return this.produtoViewList;
  }

  private List<Produto> listarProdutoPorEstoque(ZipZopDataBase dataBase) {
    try {
      return new ListarProdutoPorEstoqueTask(dataBase.getEstoqueProdutoDAO(), this.estoqueId).execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
