package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = VendaLocal.class, parentColumns = "id", childColumns = "vendaLocalId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = FormaPagamento.class, parentColumns = "id", childColumns = "formaPagamentoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Caixa.class, parentColumns = "id", childColumns = "caixaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class Venda {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private Integer valorPago;
    @NonNull
    private Integer valorVenda;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean aberta = true;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataPagamento;

    @ColumnInfo(index = true)
    @NonNull
    private Integer vendaLocalId;
    @ColumnInfo(index = true)
    @NonNull
    private Integer formaPagamentoId;
    @ColumnInfo(index = true)
    @NonNull
    private Integer caixaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValorPago() {
        return valorPago;
    }

    public void setValorPago(Integer valorPago) {
        this.valorPago = valorPago;
    }

    public Integer getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Integer valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Boolean getAberta() {
        return aberta;
    }

    public void setAberta(Boolean aberta) {
        this.aberta = aberta;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
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
