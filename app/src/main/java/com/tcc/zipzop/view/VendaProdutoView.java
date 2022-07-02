package com.tcc.zipzop.view;

import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.VendaProduto;

public class VendaProdutoView {
    private VendaProduto vendaProduto;
    private CaixaProdutoView caixaProdutoView;

    public VendaProduto getVendaProduto() {
        return vendaProduto;
    }

    public void setVendaProduto(VendaProduto vendaProduto) {
        this.vendaProduto = vendaProduto;
    }


    public CaixaProdutoView getCaixaProdutoView() {
        return caixaProdutoView;
    }

    public void setCaixaProdutoView(CaixaProdutoView caixaProdutoView) {
        this.caixaProdutoView = caixaProdutoView;
    }

    public String toString(){
        return this.caixaProdutoView.getCaixaProduto().getProdutoId() +"----"+ this.caixaProdutoView.getProdutoView().getProduto().getNome()
                +"----"+ this.caixaProdutoView.getProdutoView().getProduto().getPreco()
                +"----"+ this.vendaProduto.getQtd() +"----"+ this.vendaProduto.getPrecoVenda();
    }
}
