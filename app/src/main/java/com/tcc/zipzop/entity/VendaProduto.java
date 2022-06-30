package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = { @ForeignKey(entity = Venda.class, parentColumns = "id", childColumns = "vendaId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = CaixaProduto.class, parentColumns = "id", childColumns = "caixaProdutoId", onUpdate = ForeignKey.RESTRICT, onDelete = ForeignKey.RESTRICT)})
public class VendaProduto {

    @PrimaryKey
    @NonNull
    private Integer id;
    @NonNull
    private Integer qtd;
    private Integer precoVenda;

    @ColumnInfo(index = true)
    private Integer vendaId;
    @ColumnInfo(index = true)
    @NonNull
    private Integer caixaProdutoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Integer getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Integer precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
    }

    public Integer getCaixaProdutoId() {
        return caixaProdutoId;
    }

    public void setCaixaProdutoId(Integer caixaProdutoId) {
        this.caixaProdutoId = caixaProdutoId;
    }

    @Override
    public String toString() {
        return this.id +"----"+ this.qtd+"----"+this.precoVenda+"----"+this.vendaId+"----"+caixaProdutoId+"////";
    }
}
