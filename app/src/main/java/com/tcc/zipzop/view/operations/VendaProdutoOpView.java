package com.tcc.zipzop.view.operations;

import com.tcc.zipzop.entity.VendaProduto;

public class VendaProdutoOpView {
    private VendaProduto vendaProduto;
    private CaixaProdutoOpView caixaProdutoOpView;

    public VendaProduto getVendaProduto() {
        return vendaProduto;
    }

    public void setVendaProduto(VendaProduto vendaProduto) {
        this.vendaProduto = vendaProduto;
    }


    public CaixaProdutoOpView getCaixaProdutoView() {
        return caixaProdutoOpView;
    }

    public void setCaixaProdutoView(CaixaProdutoOpView caixaProdutoOpView) {
        this.caixaProdutoOpView = caixaProdutoOpView;
    }

    public String toString(){
        return this.caixaProdutoOpView.getCaixaProduto().getProdutoId() +"----"+ this.caixaProdutoOpView.getProdutoView().getProduto().getNome()
                +"----"+ this.caixaProdutoOpView.getProdutoView().getProduto().getPreco()
                +"----"+ this.vendaProduto.getQtd() +"----"+ this.vendaProduto.getPrecoVenda();
    }
}
