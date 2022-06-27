package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;


public class SalvarCaixaTask extends AsyncTask<Void, Void, Void> {
    private final CaixaDAO dao;


    public SalvarCaixaTask(CaixaDAO dao){
        this.dao=dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar();
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
