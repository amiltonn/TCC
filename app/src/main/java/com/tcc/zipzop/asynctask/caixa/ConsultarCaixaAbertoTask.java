package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;

import java.util.List;

public class ConsultarCaixaAbertoTask extends AsyncTask<Void, Void, Caixa> {
    private final CaixaDAO dao;

    public ConsultarCaixaAbertoTask(CaixaDAO dao){

        this.dao = dao;
    }
    @Override
    protected Caixa doInBackground(Void... voids) {

        return dao.caixaAberto();
    }
}
