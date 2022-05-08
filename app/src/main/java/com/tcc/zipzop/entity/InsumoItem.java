package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class InsumoItem {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Integer qtd_insumo_intem;
    private LocalDateTime data_alteracao;
    private Integer insumo_id; //FK
    private Integer item_id; //FK

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtd_insumo_intem() {
        return qtd_insumo_intem;
    }

    public void setQtd_insumo_intem(Integer qtd_insumo_intem) {
        this.qtd_insumo_intem = qtd_insumo_intem;
    }

    public LocalDateTime getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(LocalDateTime data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public Integer getInsumo_id() {
        return insumo_id;
    }

    public void setInsumo_id(Integer insumo_id) {
        this.insumo_id = insumo_id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }
}
