package com.tcc.zipzop.entity;

public class EstoqueItem {

    private Long estoque_id; //UQ FK
    private Long item_id; // UQ FK

    public Long getEstoque_id() {
        return estoque_id;
    }

    public void setEstoque_id(Long estoque_id) {
        this.estoque_id = estoque_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }
}
