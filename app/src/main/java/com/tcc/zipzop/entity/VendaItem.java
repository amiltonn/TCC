package com.tcc.zipzop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "venda_item")
public class VendaItem {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Integer qtd;
    private Float preco_venda;
    private Integer venda_id; //FK
    private Integer caixa_item_id; //FK

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Float getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(Float preco_venda) {
        this.preco_venda = preco_venda;
    }

    public Integer getVenda_id() {
        return venda_id;
    }

    public void setVenda_id(Integer venda_id) {
        this.venda_id = venda_id;
    }

    public Integer getCaixa_item_id() {
        return caixa_item_id;
    }

    public void setCaixa_item_id(Integer caixa_item_id) {
        this.caixa_item_id = caixa_item_id;
    }
}
