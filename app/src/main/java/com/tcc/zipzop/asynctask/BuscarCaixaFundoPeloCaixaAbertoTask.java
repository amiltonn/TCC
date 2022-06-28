package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.CaixaFundo;

public class BuscarCaixaFundoPeloCaixaAbertoTask extends AsyncTask<Void, Void, CaixaFundo> {
    CaixaFundoDAO dao;
    Integer id;

    public BuscarCaixaFundoPeloCaixaAbertoTask(CaixaFundoDAO dao, Integer id) {
        this.dao = dao;
        this.id  = id;
    }

    @Override
    protected CaixaFundo doInBackground(Void... voids) {
        return dao.buscarpelocaixaaberto(id);
    }
}
