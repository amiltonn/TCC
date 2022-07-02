package com.tcc.zipzop.asynctask.caixa;

import com.tcc.zipzop.CaixaAbertoActivity;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.produto.EditarProdutoTask;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FecharCaixaActivityTask extends FecharCaixaTask {
    private final CaixaDAO dao;
    private final CaixaProdutoDAO caixaProdutoDAO;
    private final ProdutoDAO produtoDAO;
    private final CaixaAbertoActivity caixaAbertoActivity;
    private List<CaixaProduto> caixaProdutoList;

    public FecharCaixaActivityTask(CaixaDAO dao, CaixaProdutoDAO caixaProdutoDAO, ProdutoDAO produtoDAO, CaixaAbertoActivity caixaAbertoActivity) {
        super(dao);
        this.dao = dao;
        this.caixaProdutoDAO = caixaProdutoDAO;
        this.produtoDAO = produtoDAO;
        this.caixaAbertoActivity = caixaAbertoActivity;
    }

    @Override
    protected void onPreExecute() {
        try {
            Caixa caixa = new ConsultarCaixaAbertoTask(dao).execute().get();
            caixaProdutoList = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO, caixa.getId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        restaurarQtdProduto();
        caixaAbertoActivity.fecharCaixaSucesso();
    }

    private void restaurarQtdProduto() {
        try {
            for (CaixaProduto caixaProduto : caixaProdutoList) {
                Produto produto = new ConsultarProdutoTask(produtoDAO, caixaProduto.getProdutoId()).execute().get();
                produto.setQtd(produto.getQtd() + caixaProduto.getQtd());
                new EditarProdutoTask(produtoDAO, produto).execute();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
