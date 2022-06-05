package com.tcc.zipzop.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"estoqueId", "produtoId"})
public class EstoqueProduto {

    private Integer estoqueId;
    private  Integer produtoId;

}
