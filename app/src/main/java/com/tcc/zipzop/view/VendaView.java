package com.tcc.zipzop.view;

import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

public class VendaView {
    private Venda venda;
    private FormaPagamento formaPagamento;
    private List<VendaProdutoView> vendaProdutoViewList;

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

    public List<VendaProdutoView> getVendaProdutoViewList() {
        return vendaProdutoViewList;
    }

    public void setVendaProdutoViewList(List<VendaProdutoView> vendaProdutoViewList) {
        this.vendaProdutoViewList = vendaProdutoViewList;
    }

    public String toString(){
        return "valorPago: " + this.venda.getValorPago();
    }
}
