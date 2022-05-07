package com.tcc.zipzop.entity;

public class UnidadeMedida {

    private Long id;
    private String nome; //UQ

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
}
