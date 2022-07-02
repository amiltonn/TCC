package com.tcc.zipzop.view;

import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.List;

public class VendaView {
    private Venda venda;
    private FormaPagamento formaPagamento;
    private List<VendaProduto> vendaProdutoList;

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

    public List<VendaProduto> getVendaProdutoList() {
        return vendaProdutoList;
    }

    public void setVendaProdutoList(List<VendaProduto> vendaProdutoList) {
        this.vendaProdutoList = vendaProdutoList;
    }
}
