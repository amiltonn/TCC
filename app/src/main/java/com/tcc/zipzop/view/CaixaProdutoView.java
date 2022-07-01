package com.tcc.zipzop.view;

public class CaixaProdutoView {

    private Integer id;
    private Integer qtd;
    private Integer produtoId;
    private String produtoNome;
    private Integer produtoPreco;

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

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getProdutoPreco() {
        return produtoPreco;
    }

    public void setProdutoPreco(Integer produtoPreco) {
        this.produtoPreco = produtoPreco;
    }

    public String toString(){
        return this.produtoNome +" Qtd: "+ this.qtd;
    }
}
