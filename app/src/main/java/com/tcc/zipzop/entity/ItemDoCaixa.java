package com.tcc.zipzop.entity;

public class ItemDoCaixa {

    private Integer id;
    private String nome;
    private Integer qtdSelecionada;

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

    public Integer getQtdSelecionada() {
        return qtdSelecionada;
    }

    public void setQtdSelecionada(Integer qtdSelecionada) {
        this.qtdSelecionada = qtdSelecionada;
    }
}
