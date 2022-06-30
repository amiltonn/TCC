package com.tcc.zipzop.asynctask.produto;

import android.os.AsyncTask;

import com.tcc.zipzop.SalvarProdutoActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class EditarProdutoTask extends AsyncTask<Void, Void, Void> {

    private final ProdutoDAO dao;
    private final SalvarProdutoActivity salvarProdutoActivity;
    private final Produto produto;

    public EditarProdutoTask(
            ProdutoDAO dao,
            SalvarProdutoActivity salvarProdutoActivity,
            Produto produto
    ){
        this.dao = dao;
        this.salvarProdutoActivity = salvarProdutoActivity;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.alterar(produto);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        salvarProdutoActivity.salvarComSucesso();
    }

}
