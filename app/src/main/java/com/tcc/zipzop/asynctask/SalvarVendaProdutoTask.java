package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.VendaProduto;

public class SalvarVendaProdutoTask extends AsyncTask<Void, Void, Void > {
    private VendaProdutoDAO dao;
    private VendaProduto vendaProduto;

    public SalvarVendaProdutoTask(VendaProdutoDAO dao, VendaProduto vendaProduto) {
        this.dao = dao;
        this.vendaProduto = vendaProduto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(vendaProduto);
        return null;
    }
}
