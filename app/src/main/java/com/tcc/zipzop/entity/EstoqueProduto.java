package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"estoqueId", "produtoId"})
public class EstoqueProduto {

    @NonNull
    private Integer estoqueId;
    @NonNull
    private  Integer produtoId;

}
