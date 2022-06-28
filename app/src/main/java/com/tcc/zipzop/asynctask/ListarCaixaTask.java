package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;

import java.util.List;

public class ListarCaixaTask extends AsyncTask<Void, Void, List<Caixa>> {
    private final CaixaDAO dao;

    public ListarCaixaTask(CaixaDAO dao){

        this.dao = dao;
    }
    @Override
    protected List<Caixa> doInBackground(Void... voids) {

        return dao.listar();
    }
}
