package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VendaProduto {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer qtd;
    private Float precoVenda;
    private Integer vendaId; //FK
    private Integer caixaProdutoId; //FK

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
    }

    public Integer getCaixaProdutoId() {
        return caixaProdutoId;
    }

    public void setCaixaProdutoId(Integer caixaProdutoId) {
        this.caixaProdutoId = caixaProdutoId;
    }
}
