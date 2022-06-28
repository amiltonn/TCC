package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = Estoque.class, parentColumns = "id", childColumns = "estoqueId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class Caixa {

    @PrimaryKey
    @NonNull
    private Integer id;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAbertura = new Date();
    private Date dataFechamento;

    @ColumnInfo(index = true)
    @NonNull
    private Integer estoqueId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Integer getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Integer estoqueId) {
        this.estoqueId = estoqueId;
    }
    @Override
    public String toString() {
        return this.id+"////"+ this.dataAbertura+"////"+ this.dataFechamento +"////"+ this.estoqueId;
    }
}
