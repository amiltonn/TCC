package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

import java.util.List;

public class ListarCaixaProdutoTask extends AsyncTask<Void, Void, List<CaixaProduto>> {
    private CaixaProdutoDAO dao;

    public ListarCaixaProdutoTask(CaixaProdutoDAO dao){

        this.dao = dao;
    }

    @Override
    protected List<CaixaProduto> doInBackground(Void... voids) {

        return dao.listar();

    }
}