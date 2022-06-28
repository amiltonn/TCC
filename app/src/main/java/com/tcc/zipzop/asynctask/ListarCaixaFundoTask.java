package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;


import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.CaixaFundo;

import java.util.List;

public class ListarCaixaFundoTask extends AsyncTask<Void, Void, List<CaixaFundo>> {
    private CaixaFundoDAO dao;

    public ListarCaixaFundoTask(CaixaFundoDAO dao){

        this.dao = dao;
    }

    @Override
    protected List<CaixaFundo> doInBackground(Void... voids) {

        return dao.listar();

    }
}
