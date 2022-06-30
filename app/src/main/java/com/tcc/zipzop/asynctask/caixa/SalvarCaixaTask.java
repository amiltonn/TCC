package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;


public class SalvarCaixaTask extends AsyncTask<Void, Void, Void> {
    private final CaixaDAO dao;
    private final AbrirCaixaActivity abrirCaixaActivity;


    public SalvarCaixaTask(CaixaDAO dao, AbrirCaixaActivity abrirCaixaActivity){
        this.dao = dao;
        this.abrirCaixaActivity = abrirCaixaActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar();
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        abrirCaixaActivity.salvarCaixaFundo();
    }
}
