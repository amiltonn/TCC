package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = Produto.class, parentColumns = "id", childColumns = "produtoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Caixa.class, parentColumns = "id", childColumns = "caixaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class CaixaProduto {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private Integer qtd;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean ativo = true;
    @ColumnInfo(defaultValue = "1")
    @NonNull
    private Boolean atual = true;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAlteracao = new Date();

    @ColumnInfo(index = true)
    @NonNull
    private Integer produtoId;
    @ColumnInfo(index = true)
    @NonNull
    private Integer caixaId;

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
    @Override
    public String toString() {
        return this.id+"////"+ this.qtd+"////"+ this.produtoId +"////"+ this.caixaId;
    }
}
