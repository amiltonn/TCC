package com.tcc.zipzop.asynctask.produto;

import android.os.AsyncTask;

import com.tcc.zipzop.SalvarProdutoActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class SalvarProdutoTask extends AsyncTask<Void, Void, Void> {

    private final ProdutoDAO dao;
    private final Produto produto;

    public SalvarProdutoTask(ProdutoDAO dao, Produto produto){
        this.dao = dao;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(produto);
        return null;
    }
}
