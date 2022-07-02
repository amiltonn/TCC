package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoPorProdutoAntesIdTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.produto.EditarProdutoTask;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.view.CaixaProdutoView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


public class SalvarCaixaActivityTask extends AsyncTask<Void, Void, List<CaixaProdutoView>> {

    private final CaixaDAO dao;
    private final ProdutoDAO produtoDAO;
    private List<CaixaProdutoView> listaCaixaProdutoView;
    private final AbrirCaixaActivity abrirCaixaActivity;


    public SalvarCaixaActivityTask(CaixaDAO dao, ProdutoDAO produtoDAO, List<CaixaProdutoView> listaCaixaProdutoView, AbrirCaixaActivity abrirCaixaActivity){
        this.produtoDAO = produtoDAO;
        this.listaCaixaProdutoView = listaCaixaProdutoView;
        this.abrirCaixaActivity = abrirCaixaActivity;
        this.dao = dao;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPreExecute() {
        listaCaixaProdutoView = listaCaixaProdutoView.stream().map(this::alterarQtdProdutoView).collect(Collectors.toList());
    }

    @Override
    protected List<CaixaProdutoView> doInBackground(Void... voids) {
        dao.salvar();
        return listaCaixaProdutoView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(List<CaixaProdutoView> listaCPV) {
        super.onPostExecute(listaCPV);
        abrirCaixaActivity.salvarCaixaFundo();
    }

    private CaixaProdutoView alterarQtdProdutoView(CaixaProdutoView caixaProdutoView) {
        Produto produto = caixaProdutoView.getProdutoView().getProduto();
        CaixaProduto caixaProduto = caixaProdutoView.getCaixaProduto();
        try {
            if(produto.getQtd() < caixaProduto.getQtd()) {
                throw new Exception("Quantidade de Produtos do Caixa passou Produtos em estoque!");
            } else {
                produto.setQtd(produto.getQtd() - caixaProduto.getQtd());
                new EditarProdutoTask(produtoDAO, produto).execute();
                caixaProdutoView.getProdutoView().setProduto(new ConsultarProdutoPorProdutoAntesIdTask(produtoDAO, produto.getId()).execute().get());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return caixaProdutoView;
    }
}
