package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;

public class ChecarCaixaAbertoTask extends AsyncTask<Void, Void, Boolean> {
    private final CaixaDAO dao;

    public ChecarCaixaAbertoTask(CaixaDAO dao) {
        this.dao = dao;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return dao.existeCaixaAberto();
    }
}
