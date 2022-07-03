package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Caixa;

public class ConsultarCaixaTask extends AsyncTask<Void, Void, Caixa> {
    private final CaixaDAO dao;
    private final Integer id;

    public ConsultarCaixaTask(CaixaDAO dao, Integer id){
        this.dao = dao;
        this.id = id;
    }
    @Override
    protected Caixa doInBackground(Void... voids) {
        return dao.consultar(this.id);
    }
}
