package com.tcc.zipzop.view.operations;

import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

public class VendaOpView {
    private Venda venda;
    private FormaPagamento formaPagamento;
    private List<VendaProdutoOpView> vendaProdutoOpViewList;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<VendaProdutoOpView> getVendaProdutoViewList() {
        return vendaProdutoOpViewList;
    }

    public void setVendaProdutoViewList(List<VendaProdutoOpView> vendaProdutoOpViewList) {
        this.vendaProdutoOpViewList = vendaProdutoOpViewList;
    }

    public String toString(){
        return "valorPago: " + this.venda.getValorPago();
    }
}
