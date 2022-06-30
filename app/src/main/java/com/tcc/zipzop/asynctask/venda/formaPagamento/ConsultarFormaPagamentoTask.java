package com.tcc.zipzop.asynctask.venda.formaPagamento;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.FormaPagamentoDAO;
import com.tcc.zipzop.entity.FormaPagamento;

public class ConsultarFormaPagamentoTask extends AsyncTask <Void, Void, FormaPagamento> {

    private final FormaPagamentoDAO dao;
    private final Integer id;

    public ConsultarFormaPagamentoTask(
            FormaPagamentoDAO dao,
            Integer id
    ){
            this.dao = dao;
            this.id = id;
    }


    @Override
    protected FormaPagamento doInBackground(Void... voids) {
        return dao.consultar(this.id);
    }
    
//    @Override
//    protected void onPostExecute(FormaPagamento FormaPagamento) {
//        super.onPostExecute(FormaPagamento);
//    }
}
