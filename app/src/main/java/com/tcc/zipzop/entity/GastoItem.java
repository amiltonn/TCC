package com.tcc.zipzop.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"gastoId", "itemId"})
public class GastoItem {

    private Integer gastoId;
    private  Integer itemId;

}
