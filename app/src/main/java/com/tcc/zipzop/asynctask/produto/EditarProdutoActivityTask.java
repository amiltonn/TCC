package com.tcc.zipzop.asynctask.produto;

import android.os.AsyncTask;

import com.tcc.zipzop.SalvarProdutoActivity;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class EditarProdutoActivityTask extends EditarProdutoTask {

    private final SalvarProdutoActivity salvarProdutoActivity;

    public EditarProdutoActivityTask(
            ProdutoDAO dao,
            SalvarProdutoActivity salvarProdutoActivity,
            Produto produto
    ){
        super(dao, produto);
        this.salvarProdutoActivity = salvarProdutoActivity;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        salvarProdutoActivity.salvarComSucesso();
    }

}
