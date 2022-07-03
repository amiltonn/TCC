package com.tcc.zipzop.asynctask.venda.vendaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.List;

public class ListarVendaProdutoPorVendaTask extends AsyncTask<Void, Void, List<VendaProduto>> {
    private final VendaProdutoDAO dao;
    private final Integer id;

    public ListarVendaProdutoPorVendaTask(VendaProdutoDAO dao, Integer id) {
        this.dao = dao;
        this.id = id;
    }

    @Override
    protected List<VendaProduto> doInBackground(Void... voids) {
        return dao.listarPorVendaId(this.id);
    }
}
