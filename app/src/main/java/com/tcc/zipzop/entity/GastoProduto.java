package com.tcc.zipzop.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"gastoId", "produtoId"})
public class GastoProduto {

    private Integer gastoId;
    private  Integer produtoId;

}
