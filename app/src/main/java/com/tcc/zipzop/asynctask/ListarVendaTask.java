package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.Venda;

import java.util.List;

public class ListarVendaTask extends AsyncTask <Void, Void, List<Venda>> {

    private final VendaDAO dao;

    public ListarVendaTask(
            VendaDAO dao
    ){
        this.dao = dao;
    }

    @Override
    protected List<Venda> doInBackground(Void... voids) {
        return dao.listar();
    }

}
