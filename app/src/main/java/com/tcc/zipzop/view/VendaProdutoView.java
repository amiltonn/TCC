package com.tcc.zipzop.view;

public class VendaProdutoView {

    private Integer id;
    private String nome;
    private Integer preco;
    private Integer qtdCaixa;
    private Integer qtdSelecionada;
    private Integer precoVenda;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return this.id +"----"+ this.nome +"----"+ this.preco +"----"+ qtdCaixa +"----"+ qtdSelecionada +"----"+ precoVenda;
    }
}
