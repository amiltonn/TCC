package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Insumo {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Integer item_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }
}
