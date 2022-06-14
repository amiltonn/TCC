package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Gasto {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private String nome;
    @NonNull
    private Integer custo;
    @NonNull
    private Date dataPagamento;

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

    public Integer getCusto() {
        return custo;
    }

    public void setCusto(Integer custo) {
        this.custo = custo;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
