package com.tcc.zipzop.entity;

import android.widget.EditText;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "item")
public class Item implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String nome;
    private Integer qtd;
    private Float custo;
    private Float preco;
    private Boolean ativo;
    private Boolean atual;
    private String data_alteracao;
    private Integer item_antes_id;
    private Integer unidade_medida_id; //FK



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Float getCusto() {
        return custo;
    }

    public void setCusto(Float custo) {
        this.custo = custo;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
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

    public String getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(String data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public Integer getItem_antes_id() {
        return item_antes_id;
    }

    public void setItem_antes_id(Integer item_antes_id) {
        this.item_antes_id = item_antes_id;
    }

    public Integer getUnidade_medida_id() {
        return unidade_medida_id;
    }

    public void setUnidade_medida_id(Integer unidade_medida_id) {
        this.unidade_medida_id = unidade_medida_id;
    }
}
