package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"estoqueId", "produtoId"},
    foreignKeys = { @ForeignKey(entity = Estoque.class, parentColumns = "id", childColumns = "estoqueId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                    @ForeignKey(entity = Produto.class, parentColumns = "id", childColumns = "produtoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class EstoqueProduto {

    @ColumnInfo(index = true)
    @NonNull
    private Integer estoqueId;
    @ColumnInfo(index = true)
    @NonNull
    private  Integer produtoId;

    public Integer getEstoqueId() {
        return estoqueId;
    }

    public void setEstoqueId(Integer estoqueId) {
        this.estoqueId = estoqueId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }
}
