package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.CaixaFundo;

public class SalvarCaixaFundoTask extends AsyncTask<Void, Void, Void> {
    private CaixaFundoDAO dao;
    private CaixaFundo caixaFundo;

    public SalvarCaixaFundoTask(CaixaFundoDAO dao, CaixaFundo caixaFundo){
        this.dao=dao;
        this.caixaFundo =caixaFundo;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(caixaFundo);
        return null;
    }

}
