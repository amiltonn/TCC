package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Formula {

    @PrimaryKey
    @NonNull
    private Integer id;
    @ColumnInfo(defaultValue = "0")
    @NonNull
    private Boolean aberta = false;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAberta() {
        return aberta;
    }
    public void setAberta(Boolean aberta) {
        this.aberta = aberta;
    }
}
