package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"gastoId", "produtoId"})
public class GastoProduto {

    @NonNull
    private Integer gastoId;
    @NonNull
    private  Integer produtoId;

}
