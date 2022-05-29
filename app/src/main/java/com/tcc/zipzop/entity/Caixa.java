package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "caixa")
public class Caixa {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Float fundo;
    private String data_abertura;
    private String data_fechamento;
    private Integer estoque_id; //FK

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getFundo() {
        return fundo;
    }

    public void setFundo(Float fundo) {
        this.fundo = fundo;
    }

    public String getData_abertura() {
        return data_abertura;
    }

    public void setData_abertura(String data_abertura) {
        this.data_abertura = data_abertura;
    }

    public String getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(String data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public Integer getEstoque_id() {
        return estoque_id;
    }

    public void setEstoque_id(Integer estoque_id) {
        this.estoque_id = estoque_id;
    }
}
