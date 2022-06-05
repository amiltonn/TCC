package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.PropertyName;

@Entity(indices = {@Index(value = {"nome"}, unique = true)})
public class UnidadeMedida {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    
    private String nome;

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
}
