package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;

import com.tcc.zipzop.CaixaAbertoActivity;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FecharCaixaTask extends AsyncTask<Void, Void, Void> {
    private final CaixaDAO dao;
    private final CaixaAbertoActivity fecharCaixaActivity;

    public FecharCaixaTask(CaixaDAO dao, CaixaAbertoActivity fecharCaixaActivity) {
        this.dao = dao;
        this.fecharCaixaActivity = fecharCaixaActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        restaurarQtdProduto();
        dao.fechar();
        return null;
    }

    private void restaurarQtdProduto() {
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(fecharCaixaActivity);
        CaixaProdutoDAO caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        ProdutoDAO produtoDAO = dataBase.getProdutoDAO();
        List<CaixaProduto> caixaProdutoList = new ArrayList<>();
        Produto produto;
        try {
            Caixa caixa = new ConsultarCaixaAbertoTask(dao).get();
            caixaProdutoList = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO, caixa.getId()).get();
            for (CaixaProduto caixaProduto : caixaProdutoList) {
                produto = new ConsultarProdutoTask(produtoDAO, caixaProduto.getProdutoId()).get();
                produto.setQtd(produto.getQtd() + caixaProduto.getQtd());
                produtoDAO.alterar(produto);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
