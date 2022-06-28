package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;

public class ConsultarCaixaAbertoTask extends AsyncTask<Void, Void, Boolean> {
    private final CaixaDAO dao;

    public ConsultarCaixaAbertoTask(CaixaDAO dao) {
        this.dao = dao;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return dao.existeCaixaAberto();
    }
}
