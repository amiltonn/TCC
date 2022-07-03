package com.tcc.zipzop.view.analytics;

import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;

public class VendaView {

    private Venda venda;
    private FormaPagamento formaPagamento;
    private CaixaView caixaView;

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

    public CaixaView getCaixaView() {
        return caixaView;
    }

    public void setCaixaView(CaixaView caixaView) {
        this.caixaView = caixaView;
    }
}
