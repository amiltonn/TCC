package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;

import java.util.List;

public class ListarCaixaAbertoTask extends AsyncTask<Void, Void, Caixa> {
    private final CaixaDAO dao;

    public ListarCaixaAbertoTask(CaixaDAO dao){

        this.dao = dao;
    }
    @Override
    protected Caixa doInBackground(Void... voids) {

        return dao.caixaAberto();
    }
}
