package com.tcc.zipzop.asynctask.caixa.caixaFundo;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.CaixaFundo;

public class SalvarCaixaFundoTask extends AsyncTask<Void, Void, Void> {
    private CaixaFundoDAO dao;
    private CaixaFundo caixaFundo;
    private final AbrirCaixaActivity abrirCaixaActivity;


    public SalvarCaixaFundoTask(CaixaFundoDAO dao, CaixaFundo caixaFundo, AbrirCaixaActivity abrirCaixaActivity){
        this.dao = dao;
        this.caixaFundo = caixaFundo;
        this.abrirCaixaActivity = abrirCaixaActivity;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(caixaFundo);
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        abrirCaixaActivity.salvarCaixaProduto();
    }

}
