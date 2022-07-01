package com.tcc.zipzop.asynctask.produto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

public class ConsultarProdutoTask extends AsyncTask <Void, Void, Produto> {

    private final ProdutoDAO dao;
    private final Integer id;

    public ConsultarProdutoTask(
            ProdutoDAO dao,
            Integer id
    ){
            this.dao = dao;
            this.id = id;
    }


    @Override
    protected Produto doInBackground(Void... voids) {
        return dao.consultar(this.id);
    }


}
