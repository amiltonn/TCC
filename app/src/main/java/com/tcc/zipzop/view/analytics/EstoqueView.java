package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.Estoque;

import java.util.List;

public class EstoqueView {

  private Estoque estoque;
  private List<ProdutoView> produtoViewList;

  public Estoque getEstoque() {
    return estoque;
  }

  public void setEstoque(Estoque estoque) {
    this.estoque = estoque;
  }

  public List<ProdutoView> getProdutoViewList() {
    return produtoViewList;
  }

  public void setProdutoViewList(List<ProdutoView> produtoList) {
    this.produtoViewList = produtoList;
  }
}
