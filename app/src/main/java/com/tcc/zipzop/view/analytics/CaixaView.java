package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.view.operations.CaixaProdutoOpView;
import com.tcc.zipzop.view.operations.ProdutoOpView;

import java.util.List;

public class CaixaView {

  private Caixa caixa;
  private CaixaFundo caixaFundo;
  private List<ProdutoOpView> estoqueProdutoOpViewList;
  private List<CaixaProdutoOpView> caixaProdutoOpViewList;


  public Caixa getCaixa() {
    return caixa;
  }

  public void setCaixa(Caixa caixa) {
    this.caixa = caixa;
  }

  public CaixaFundo getCaixaFundo() {
    return caixaFundo;
  }

  public void setCaixaFundo(CaixaFundo caixaFundo) {
    this.caixaFundo = caixaFundo;
  }

  public List<ProdutoOpView> getEstoqueProdutoViewList() {
    return estoqueProdutoOpViewList;
  }

  public void setEstoqueProdutoViewList(List<ProdutoOpView> estoqueProdutoOpViewList) {
    this.estoqueProdutoOpViewList = estoqueProdutoOpViewList;
  }

  public List<CaixaProdutoOpView> getCaixaProdutoViewList() {
    return caixaProdutoOpViewList;
  }

  public void setCaixaProdutoViewList(List<CaixaProdutoOpView> caixaProdutoOpViewList) {
    this.caixaProdutoOpViewList = caixaProdutoOpViewList;
  }
}
