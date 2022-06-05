package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Insumo {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer qtdInsumoItem;
    private Boolean ativo = (true);
    private Boolean atual = (true);
    private String dataAlteracao;
    private Integer formulaId;
    private Integer insumoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdInsumoItem() {
        return qtdInsumoItem;
    }

    public void setQtdInsumoItem(Integer qtdInsumoItem) {
        this.qtdInsumoItem = qtdInsumoItem;
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

    public String getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(String dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Integer formulaId) {
        this.formulaId = formulaId;
    }

    public Integer getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }
}
