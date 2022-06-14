package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = Formula.class, parentColumns = "id", childColumns = "formulaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Produto.class, parentColumns = "id", childColumns = "insumoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class Insumo {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private Integer qtdInsumoProduto;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean ativo = (true);
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean atual = (true);
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAlteracao = new Date();

    @ColumnInfo(index = true)
    @NonNull
    private Integer formulaId;
    @ColumnInfo(index = true)
    @NonNull
    private Integer insumoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdInsumoProduto() {
        return qtdInsumoProduto;
    }

    public void setQtdInsumoProduto(Integer qtdInsumoProduto) {
        this.qtdInsumoProduto = qtdInsumoProduto;
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

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
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
