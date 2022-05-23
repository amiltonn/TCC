package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "unidade_medida")
public class UnidadeMedida {

    @PrimaryKey(autoGenerate = true)
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
