package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "caixa_item")
public class CaixaItem {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Integer qtd;

    private LocalDateTime data_alteracao;

    private Integer item_id; //FK
    private Integer caixa_id; //FK

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public LocalDateTime getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(LocalDateTime data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getCaixa_id() {
        return caixa_id;
    }

    public void setCaixa_id(Integer caixa_id) {
        this.caixa_id = caixa_id;
    }
}
