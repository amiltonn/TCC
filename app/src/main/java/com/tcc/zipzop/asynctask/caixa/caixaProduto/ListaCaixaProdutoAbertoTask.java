package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

import java.util.List;

public class ListaCaixaProdutoAbertoTask extends AsyncTask<Void, Void, List<CaixaProduto>> {
    private CaixaProdutoDAO dao;
    Integer caixaId;
    public ListaCaixaProdutoAbertoTask( CaixaProdutoDAO dao, Integer caixaId){
        this.dao = dao;
        this.caixaId = caixaId;
    }
    @Override
    protected List<CaixaProduto> doInBackground(Void... voids) {
        return dao.listarCaixaProdutoAberto(caixaId);
    }
}
