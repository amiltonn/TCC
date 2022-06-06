package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Venda {

    @PrimaryKey
    @NonNull
    private Integer id;

    private Float valorPago;
    private Float valorVenda;
    private LocalDateTime dataPagamento;
    private Integer vendaLocalId;
    private Integer formaPagamentoId; //FK
    private Integer caixaId; //FK

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValorPago() {
        return valorPago;
    }

    public void setValorPago(Float valorPago) {
        this.valorPago = valorPago;
    }

    public Float getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Float valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getVendaLocalId() {
        return vendaLocalId;
    }

    public void setVendaLocalId(Integer vendaLocalId) {
        this.vendaLocalId = vendaLocalId;
    }

    public Integer getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(Integer formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }

    public Integer getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(Integer caixaId) {
        this.caixaId = caixaId;
    }
}
