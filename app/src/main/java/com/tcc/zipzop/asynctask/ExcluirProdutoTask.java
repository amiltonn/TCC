package com.tcc.zipzop.asynctask;
import android.os.AsyncTask;

import com.tcc.zipzop.adapter.ProdutoAdapterActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class ExcluirProdutoTask extends AsyncTask<Void, Void, Void> {

    private final ProdutoDAO dao;
    private final ProdutoAdapterActivity adapter;
    private final Produto produto;

    public ExcluirProdutoTask(
            ProdutoDAO dao,
            ProdutoAdapterActivity adapter,
            Produto produto
    ){
        this.dao = dao;
        this.adapter = adapter;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deletar(produto.getId());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.excluir(produto);
    }

}
