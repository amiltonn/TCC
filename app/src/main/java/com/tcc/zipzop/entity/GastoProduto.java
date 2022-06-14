package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"gastoId", "produtoId"},
        foreignKeys = { @ForeignKey(entity = Gasto.class, parentColumns = "id", childColumns = "gastoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Produto.class, parentColumns = "id", childColumns = "produtoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class GastoProduto {

    @ColumnInfo(index = true)
    @NonNull
    private Integer gastoId;
    @ColumnInfo(index = true)
    @NonNull
    private  Integer produtoId;

    public Integer getGastoId() {
        return gastoId;
    }

    public void setGastoId(Integer gastoId) {
        this.gastoId = gastoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}
