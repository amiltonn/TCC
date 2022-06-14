package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Estoque {

    @PrimaryKey
    @NonNull
    private Integer id;
    @ColumnInfo(defaultValue = "(datetime())")
    @NonNull
    private Date dataAlteracao = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date data_alteracao) {
        this.dataAlteracao = data_alteracao;
    }
}
