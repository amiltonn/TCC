package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CaixaFundo {

    @PrimaryKey
    @NonNull
    private Integer id;

    private Float valor;
    private String dataAlteracao;
    private Integer caixaId;
}
