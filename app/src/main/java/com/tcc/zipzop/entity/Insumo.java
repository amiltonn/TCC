package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "insumo")
public class Insumo {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer qtd_insumo_item;
    private Boolean ativo = (true);
    private Boolean atual = (true);
    private String data_alteracao;
    private Integer formula_id;
    private Integer insumo_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtd_insumo_item() {
        return qtd_insumo_item;
    }

    public void setQtd_insumo_item(Integer qtd_insumo_item) {
        this.qtd_insumo_item = qtd_insumo_item;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAtual() {
        return atual;
    }

    public void setAtual(Boolean atual) {
        this.atual = atual;
    }

    public String getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(String data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public Integer getFormula_id() {
        return formula_id;
    }

    public void setFormula_id(Integer formula_id) {
        this.formula_id = formula_id;
    }

    public Integer getInsumo_id() {
        return insumo_id;
    }

    public void setInsumo_id(Integer insumo_id) {
        this.insumo_id = insumo_id;
    }
}
