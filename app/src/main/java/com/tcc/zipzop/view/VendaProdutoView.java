package com.tcc.zipzop.view;

public class VendaProdutoView {

    private Integer id;
    private Integer produtoId;
    private Integer caixaProdutoId;
    private String produtoNome;
    private Integer produtoPreco;
    private Integer qtd;
    private Integer precoVenda;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getCaixaProdutoId(){
        return caixaProdutoId;
    }

    public void setCaixaProdutoId(Integer caixaProdutoId){
        this.caixaProdutoId = caixaProdutoId;
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

    public String toString(){
        return this.produtoId +"----"+ this.produtoNome +"----"+ this.produtoPreco +"----"+ qtd +"----"+ precoVenda;
    }
}
