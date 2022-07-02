package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;

public class FecharCaixaTask extends AsyncTask<Void, Void, Void> {
    private final CaixaDAO dao;

    public FecharCaixaTask(CaixaDAO dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.fechar();
        return null;
    }
}
