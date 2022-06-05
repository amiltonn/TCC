package com.tcc.zipzop.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"estoqueId", "itemId"})
public class EstoqueItem {

    private Integer estoqueId;
    private  Integer itemId;

}
