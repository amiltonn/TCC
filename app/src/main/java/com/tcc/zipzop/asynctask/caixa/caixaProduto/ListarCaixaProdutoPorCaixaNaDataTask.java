package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;

import java.util.Date;
import java.util.List;

public class ListarCaixaProdutoPorCaixaNaDataTask extends AsyncTask<Void, Void, List<CaixaProduto>> {
    private CaixaProdutoDAO dao;
    private Integer caixaId;
    private Date dataAlteracao;

    public ListarCaixaProdutoPorCaixaNaDataTask(CaixaProdutoDAO dao, Integer caixaId, Date dataAlteracao){
        this.dao = dao;
        this.caixaId = caixaId;
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    protected List<CaixaProduto> doInBackground(Void... voids) {
        return dao.listarCaixaProdutoPorCaixaNaData(caixaId, dataAlteracao);
    }
}