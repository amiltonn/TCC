package com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.caixa.caixaFundo.ConsultarCaixaFundoPeloCaixaIdTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ConsultarCaixaTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.ConsultarEstoqueViewTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView.caixaProdutoView.ListarCaixaProdutoViewTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.view.analytics.CaixaProdutoView;
import com.tcc.zipzop.view.analytics.CaixaView;
import com.tcc.zipzop.view.analytics.EstoqueView;
import com.tcc.zipzop.view.analytics.ProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ConsultarCaixaViewTask extends AsyncTask<Void, Void, CaixaView> {
  private final AppCompatActivity activity;
  private final Integer id;
  private Caixa caixa;
  private CaixaFundo caixaFundo;
  private EstoqueView estoqueView;
  private List<CaixaProdutoView> caixaProdutoViewList;
  private CaixaView caixaView;

  public ConsultarCaixaViewTask(AppCompatActivity activity, Integer id) {
    this.activity = activity;
    this.id = id;
    this.caixa = new Caixa();
    this.caixaFundo = new CaixaFundo();
    this.estoqueView = new EstoqueView();
    this.caixaProdutoViewList = new ArrayList<>();
    this.caixaView = new CaixaView();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    try {
      this.caixa = new ConsultarCaixaTask(dataBase.getCaixaDAO(), this.id).execute().get();
      this.caixaFundo = new ConsultarCaixaFundoPeloCaixaIdTask(dataBase.getCaixaFundoDAO(), this.caixa.getId()).execute().get();
      this.estoqueView = new ConsultarEstoqueViewTask(activity, this.id).execute().get();
      this.caixaProdutoViewList = new ListarCaixaProdutoViewTask(activity, this.id, this.caixaFundo.getDataAlteracao()).execute().get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected CaixaView doInBackground(Void... voids) {
    this.caixaView.setCaixa(this.caixa);
    this.caixaView.setCaixaFundo(this.caixaFundo);
    this.caixaView.setEstoqueView(this.estoqueView);
    for (ProdutoView produtoView : this.caixaView.getEstoqueView().getProdutoViewList()) {
      Optional<CaixaProdutoView> caixaProdutoViewOptional = caixaProdutoViewList.stream()
          .filter(caixaProdutoView -> caixaProdutoView.getCaixaProduto().getProdutoId() == produtoView.getProduto().getId()).findFirst();
      if(caixaProdutoViewOptional.isPresent())
        produtoView.setCaixaProdutoView(caixaProdutoViewOptional.get());
    }
    return this.caixaView;
  }
}
