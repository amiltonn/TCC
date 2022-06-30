package com.tcc.zipzop.view;

public class VendaProdutoView {

    private Integer produtoId;
    private Integer caixaProdutoId;
    private String nome;
    private Integer preco;
    private Integer qtdCaixa;
    private Integer qtdSelecionada;
    private Integer precoVenda;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public Integer getQtdCaixa() {
        return qtdCaixa;
    }

    public void setQtdCaixa(Integer qtdCaixa) {
        this.qtdCaixa = qtdCaixa;
    }

    public Integer getQtdSelecionada() {
        return qtdSelecionada;
    }

    public void setQtdSelecionada(Integer qtdSelecionada) {
        this.qtdSelecionada = qtdSelecionada;
    }

    public Integer getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Integer precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String toString(){
        return this.produtoId +"----"+ this.nome +"----"+ this.preco +"----"+ qtdCaixa +"----"+ qtdSelecionada +"----"+ precoVenda;
    }
}
