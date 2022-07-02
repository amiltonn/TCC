package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.database.dao.CaixaDAO;


public class SalvarCaixaTask extends AsyncTask<Void, Void, Void> {
    private final CaixaDAO dao;


    public SalvarCaixaTask(CaixaDAO dao){
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar();
        return null;
    }
}
