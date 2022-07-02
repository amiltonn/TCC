package com.tcc.zipzop.asynctask.produto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class ConsultarProdutoPorProdutoAntesIdTask extends AsyncTask <Void, Void, Produto> {

    private final ProdutoDAO dao;
    private final Integer produtoAntesId;

    public ConsultarProdutoPorProdutoAntesIdTask(
            ProdutoDAO dao,
            Integer produtoAntesId
    ){
            this.dao = dao;
            this.produtoAntesId = produtoAntesId;
    }


    @Override
    protected Produto doInBackground(Void... voids) {
        return dao.consultarPorProdutoAntesId(this.produtoAntesId);
    }


}
