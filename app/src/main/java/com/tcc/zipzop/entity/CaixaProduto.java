package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class CaixaProduto {

    @PrimaryKey
    @NonNull
    private Integer id;

    private Integer qtd;

    private LocalDateTime dataAlteracao;

    private Integer produtoId; //FK
    private Integer caixaId; //FK

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

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(Integer caixaId) {
        this.caixaId = caixaId;
    }
}
