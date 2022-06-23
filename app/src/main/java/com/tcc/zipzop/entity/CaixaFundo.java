package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = { @ForeignKey(entity = Caixa.class, parentColumns = "id", childColumns = "caixaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class CaixaFundo {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private Integer valor;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAlteracao = new Date();

    @ColumnInfo(index = true)
    @NonNull
    private Integer caixaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date data_alteracao) {
        this.dataAlteracao = data_alteracao;
    }

    public Integer getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(Integer caixaId) {
        this.caixaId = caixaId;
    }
}
