package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

public class SalvarCaixaProdutoTask extends AsyncTask<Void, Void, Void> {
    private final CaixaProdutoDAO dao;
    private final CaixaProduto caixaProduto;

    public SalvarCaixaProdutoTask(CaixaProdutoDAO dao, CaixaProduto caixaProduto){
        this.dao = dao;
        this.caixaProduto = caixaProduto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(caixaProduto);
        return null;
    }
}
