package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.SalvarProdutoActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class SalvarProdutoTask extends AsyncTask<Void, Void, Void> {


    private final ProdutoDAO dao;
    private final SalvarProdutoActivity salvarProdutoActivity;
    private final Produto produto;

    public SalvarProdutoTask(ProdutoDAO dao, SalvarProdutoActivity salvarProdutoActivity, Produto produto){

        this.dao = dao;
        this.salvarProdutoActivity = salvarProdutoActivity;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(produto);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        salvarProdutoActivity.salvarComSucesso();
    }

}
