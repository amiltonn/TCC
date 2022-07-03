package com.tcc.zipzop.asynctask.estoque;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.EstoqueProdutoDAO;
import com.tcc.zipzop.entity.Produto;

import java.util.List;

public class ListarProdutoPorEstoqueTask extends AsyncTask <Void, Void, List<Produto>> {

    private final EstoqueProdutoDAO dao;
    private final Integer estoqueId;

    public ListarProdutoPorEstoqueTask(EstoqueProdutoDAO dao, Integer estoqueId){
        this.dao = dao;
        this.estoqueId = estoqueId;
    }

    @Override
    protected List<Produto> doInBackground(Void... voids) {
        return dao.listarProdutoPorEstoque(this.estoqueId);
    }
}
