package com.tcc.zipzop.view.operations;

import com.tcc.zipzop.entity.CaixaProduto;

public class CaixaProdutoOpView {
    private CaixaProduto caixaProduto;
    private ProdutoOpView produtoOpView;



    public CaixaProduto getCaixaProduto() {
        return caixaProduto;
    }

    public void setCaixaProduto(CaixaProduto caixaProduto) {
        this.caixaProduto = caixaProduto;
    }

    public ProdutoOpView getProdutoView() {
        return produtoOpView;
    }

    public void setProdutoView(ProdutoOpView produtoOpView) {
        this.produtoOpView = produtoOpView;
    }

    public String toString(){
        return this.produtoOpView.getProduto().getNome() +" Qtd: "+ this.caixaProduto.getQtd();
    }
}
