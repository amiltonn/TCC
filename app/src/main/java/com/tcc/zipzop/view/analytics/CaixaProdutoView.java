package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.CaixaProduto;

public class CaixaProdutoView {

  private CaixaProduto caixaProduto;
  private VendaProdutoView vendaProdutoView;

  public CaixaProduto getCaixaProduto() {
    return caixaProduto;
  }

  public void setCaixaProduto(CaixaProduto caixaProduto) {
    this.caixaProduto = caixaProduto;
  }

  public VendaProdutoView getVendaProdutoView() {
    return vendaProdutoView;
  }

  public void setVendaProdutoView(VendaProdutoView vendaProdutoView) {
    this.vendaProdutoView = vendaProdutoView;
  }
}
