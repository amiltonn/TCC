package com.tcc.zipzop.asynctask.relatorio.vendaView;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.ConsultarCaixaViewTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.estoqueView.produtoView.caixaProdutoView.vendaProdutoView.ListarVendaProdutoViewTask;
import com.tcc.zipzop.asynctask.venda.ConsultarVendaTask;
import com.tcc.zipzop.asynctask.venda.formaPagamento.ConsultarFormaPagamentoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.analytics.CaixaView;
import com.tcc.zipzop.view.analytics.ProdutoView;
import com.tcc.zipzop.view.analytics.VendaProdutoView;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ConsultarVendaViewTask  extends AsyncTask<Void, Void, VendaView> {
  private final AppCompatActivity activity;
  private final Integer id;
  private Venda venda;
  private FormaPagamento formaPagamento;
  private CaixaView caixaView;
  private List<VendaProdutoView> vendaProdutoViewList;
  private VendaView vendaView;

  public ConsultarVendaViewTask(AppCompatActivity activity, Integer id) {
    this.activity = activity;
    this.id = id;
    this.venda = new Venda();
    this.formaPagamento = new FormaPagamento();
    this.caixaView = new CaixaView();
    this.vendaView = new VendaView();
    this.vendaProdutoViewList = new ArrayList<>();
  }

  @Override
  protected void onPreExecute() {
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(activity);
    try {
      this.venda = new ConsultarVendaTask(dataBase.getVendaDAO(), this.id).execute().get();
      this.formaPagamento = new ConsultarFormaPagamentoTask(dataBase.getFormaPagamentoDAO(), this.venda.getFormaPagamentoId()).execute().get();
      this.caixaView = new ConsultarCaixaViewTask(activity, venda.getCaixaId()).execute().get();
      this.vendaProdutoViewList = new ListarVendaProdutoViewTask(activity, this.id).execute().get();
    } catch (ExecutionException e) {
    e.printStackTrace();
  } catch (InterruptedException e) {
    e.printStackTrace();
  }
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected VendaView doInBackground(Void... voids) {
    this.vendaView.setVenda(this.venda);
    this.vendaView.setFormaPagamento(this.formaPagamento);
    this.vendaView.setCaixaView(this.caixaView);
    for (ProdutoView produtoView : this.vendaView.getCaixaView().getEstoqueView().getProdutoViewList()) {
      Optional<VendaProdutoView> vendaProdutoViewOptional = vendaProdutoViewList.stream()
          .filter(vendaProdutoView -> produtoView.getCaixaProdutoView() != null &&
              vendaProdutoView.getVendaProduto().getCaixaProdutoId().equals(produtoView.getCaixaProdutoView().getCaixaProduto().getId())).findFirst();
      if(vendaProdutoViewOptional.isPresent())
        produtoView.getCaixaProdutoView().setVendaProdutoView(vendaProdutoViewOptional.get());
    }
    return this.vendaView;
  }
}
