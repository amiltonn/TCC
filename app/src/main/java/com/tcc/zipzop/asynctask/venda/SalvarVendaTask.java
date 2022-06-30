package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Venda;

public class SalvarVendaTask extends AsyncTask<Void, Void, Void > {

    private final VendaDAO dao;
    private final Venda venda;

    public SalvarVendaTask(VendaDAO dao, Venda venda) {
        this.dao = dao;
        this.venda = venda;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.salvar(venda);
        return null;
    }



}
