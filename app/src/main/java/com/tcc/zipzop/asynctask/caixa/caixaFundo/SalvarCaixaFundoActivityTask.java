package com.tcc.zipzop.asynctask.caixa.caixaFundo;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.CaixaFundo;

public class SalvarCaixaFundoActivityTask extends SalvarCaixaFundoTask {
    private final AbrirCaixaActivity abrirCaixaActivity;


    public SalvarCaixaFundoActivityTask(CaixaFundoDAO dao, CaixaFundo caixaFundo, AbrirCaixaActivity abrirCaixaActivity){
        super(dao, caixaFundo);
        this.abrirCaixaActivity = abrirCaixaActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        abrirCaixaActivity.salvarCaixaProduto();
    }
}
