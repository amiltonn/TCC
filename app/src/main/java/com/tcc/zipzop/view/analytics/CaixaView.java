package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;

import java.util.Date;

public class CaixaView {

  private Caixa caixa;
  private CaixaFundo caixaFundo;
  private EstoqueView estoqueView;

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

  public EstoqueView getEstoqueView() {
    return estoqueView;
  }

  public void setEstoqueView(EstoqueView estoqueView) {
    this.estoqueView = estoqueView;
  }
}
