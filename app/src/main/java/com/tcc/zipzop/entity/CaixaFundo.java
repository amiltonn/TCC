package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "caixa_fundo")
public class CaixaFundo {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Float valor;
    private String data_alteracao;
    private Integer caixa_id;
}
