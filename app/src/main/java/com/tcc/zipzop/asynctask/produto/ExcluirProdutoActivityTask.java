package com.tcc.zipzop.asynctask.produto;
import android.os.AsyncTask;

import com.tcc.zipzop.adapter.ProdutoAdapterActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class ExcluirProdutoActivityTask extends ExcluirProdutoTask {

    private final ProdutoAdapterActivity adapter;
    private final Produto produto;

    public ExcluirProdutoActivityTask(
            ProdutoDAO dao,
            ProdutoAdapterActivity adapter,
            Produto produto
    ){
        super(dao, produto);
        this.adapter = adapter;
        this.produto = produto;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.excluir(produto);
    }

}
