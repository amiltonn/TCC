package com.tcc.zipzop.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = UnidadeMedida.class, parentColumns = "id", childColumns = "unidadeMedidaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Formula.class, parentColumns = "id", childColumns = "formulaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class Produto implements Serializable {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private String nome;
    @NonNull
    private Integer qtd;
    @NonNull
    private Integer custo;
    private Integer preco;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean ativo = true;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean atual = true;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAlteracao = new Date();
    @ColumnInfo(defaultValue = "NULL")
    private Integer produtoAntesId = null;

    @ColumnInfo(index = true)
    //TODO: descomentar quando o front for usar unidade de medida
//    @NonNull
    private Integer unidadeMedidaId;
    @ColumnInfo(defaultValue = "NULL", index = true)
    private Integer formulaId = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getCusto() {
        return custo;
    }

    public void setCusto(Integer custo) {
        this.custo = custo;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
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

    public void setDataAlteracao(Date data_alteracao) {
        this.dataAlteracao = data_alteracao;
    }

    public Integer getProdutoAntesId() {
        return produtoAntesId;
    }

    public void setProdutoAntesId(Integer produtoAntesId) {
        this.produtoAntesId = produtoAntesId;
    }

    public Integer getUnidadeMedidaId() {
        return unidadeMedidaId;
    }

    public void setUnidadeMedidaId(Integer unidadeMedidaId) {
        this.unidadeMedidaId = unidadeMedidaId;
    }

    public Integer getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Integer formulaId) {
        this.formulaId = formulaId;
    }

    //???
    @Override
    public String toString() {
        return this.id + " - " + this.nome + " - " + this.qtd;
    }

}
