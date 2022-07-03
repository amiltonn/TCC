package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.UnidadeMedida;

public class ProdutoView {

  private Produto produto;
  private UnidadeMedida unidadeMedida;
  private CaixaProdutoView caixaProdutoView;

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public UnidadeMedida getUnidadeMedida() {
    return unidadeMedida;
  }

  public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
    this.unidadeMedida = unidadeMedida;
  }

  public CaixaProdutoView getCaixaProdutoView() {
    return caixaProdutoView;
  }

  public void setCaixaProdutoView(CaixaProdutoView caixaProdutoView) {
    this.caixaProdutoView = caixaProdutoView;
  }
}
