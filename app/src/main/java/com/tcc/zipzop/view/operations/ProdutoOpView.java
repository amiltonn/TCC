package com.tcc.zipzop.view.operations;

import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.UnidadeMedida;

public class ProdutoOpView {
    private Produto produto;
    private UnidadeMedida unidadeMedida;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String toString() {
        return this.produto.getNome() + " Qtd:  " + this.produto.getQtd();
    }
}
