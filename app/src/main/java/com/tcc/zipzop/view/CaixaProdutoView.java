package com.tcc.zipzop.view;

import com.tcc.zipzop.entity.CaixaProduto;

public class CaixaProdutoView {
    private CaixaProduto caixaProduto;
    private ProdutoView produtoView;



    public CaixaProduto getCaixaProduto() {
        return caixaProduto;
    }

    public void setCaixaProduto(CaixaProduto caixaProduto) {
        this.caixaProduto = caixaProduto;
    }

    public ProdutoView getProdutoView() {
        return produtoView;
    }

    public void setProdutoView(ProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public String toString(){
        return this.produtoView.getProduto().getNome() +" Qtd: "+ this.caixaProduto.getQtd();
    }
}
