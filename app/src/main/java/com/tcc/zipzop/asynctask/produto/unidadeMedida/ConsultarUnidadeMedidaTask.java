package com.tcc.zipzop.asynctask.produto.unidadeMedida;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.UnidadeMedidaDAO;
import com.tcc.zipzop.entity.UnidadeMedida;

public class ConsultarUnidadeMedidaTask extends AsyncTask <Void, Void, UnidadeMedida> {

    private final UnidadeMedidaDAO dao;
    private final Integer id;

    public ConsultarUnidadeMedidaTask(UnidadeMedidaDAO dao, Integer id){
        this.dao = dao;
        this.id = id;
    }

    @Override
    protected UnidadeMedida doInBackground(Void... voids) {
        return dao.consultar(this.id);
    }
}
