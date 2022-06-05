package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

import java.util.List;

public class ListarProdutoTask extends AsyncTask <Void, Void, List<Produto>> {

    private final ProdutoDAO dao;

    public ListarProdutoTask(
            ProdutoDAO dao
    ){
            this.dao = dao;
    }


    @Override
    protected List<Produto> doInBackground(Void... voids) {
        return dao.listar();
    }

//    @Override
//    protected void onPostExecute(List<Produto> produtos) {
//        super.onPostExecute(produtos);
//    }
}
