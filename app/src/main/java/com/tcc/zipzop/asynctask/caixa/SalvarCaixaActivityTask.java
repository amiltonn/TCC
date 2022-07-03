package com.tcc.zipzop.asynctask.caixa;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoPorProdutoAntesIdTask;
import com.tcc.zipzop.asynctask.produto.EditarProdutoTask;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.view.operations.CaixaProdutoOpView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SalvarCaixaActivityTask extends AsyncTask<Void, Void, List<CaixaProdutoOpView>> {

    private final CaixaDAO dao;
    private final ProdutoDAO produtoDAO;
    private List<CaixaProdutoOpView> listaCaixaProdutoOpView;
    private final AbrirCaixaActivity abrirCaixaActivity;
    private final Boolean invalido;


    public SalvarCaixaActivityTask(CaixaDAO dao, ProdutoDAO produtoDAO, List<CaixaProdutoOpView> listaCaixaProdutoOpView, AbrirCaixaActivity abrirCaixaActivity){
        this.produtoDAO = produtoDAO;
        this.listaCaixaProdutoOpView = listaCaixaProdutoOpView;
        this.abrirCaixaActivity = abrirCaixaActivity;
        this.dao = dao;
        invalido =  listaCaixaProdutoOpView.stream().map(caixaProdutoView -> caixaProdutoView.getProdutoView().getProduto().getQtd() < caixaProdutoView.getCaixaProduto().getQtd()).collect(Collectors.toList()).contains(true);
    }

    @Override
    protected void onPreExecute() {
        try {
            if(!invalido)
                listaCaixaProdutoOpView = listaCaixaProdutoOpView.stream().map(this::alterarQtdProdutoView).collect(Collectors.toList());
            else
                throw new Exception("Quantidade de Produtos do Caixa passou Produtos em estoque!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<CaixaProdutoOpView> doInBackground(Void... voids) {
        try {
            if(!invalido) {
                dao.salvar();
                return listaCaixaProdutoOpView;
            } else {
                throw new Exception("Quantidade de Produtos do Caixa passou Produtos em estoque!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCorrigida();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(List<CaixaProdutoOpView> listaCPV) {
        super.onPostExecute(listaCPV);
        if(!invalido)
            abrirCaixaActivity.salvarCaixaFundo();
        else {
            Toast.makeText(abrirCaixaActivity, "Removendo quantidades maiores do que permitido!", Toast.LENGTH_SHORT).show();
            abrirCaixaActivity.corrigirSpinner(listaCorrigida());
            abrirCaixaActivity.finish();
        }
    }

    private CaixaProdutoOpView alterarQtdProdutoView(CaixaProdutoOpView caixaProdutoOpView) {
        Produto produto = caixaProdutoOpView.getProdutoView().getProduto();
        CaixaProduto caixaProduto = caixaProdutoOpView.getCaixaProduto();
        try {
            if(produto.getQtd() < caixaProduto.getQtd()) {
                throw new Exception("Quantidade de Produtos do Caixa passou Produtos em estoque!");
            } else {
                produto.setQtd(produto.getQtd() - caixaProduto.getQtd());
                new EditarProdutoTask(produtoDAO, produto).execute();
                caixaProdutoOpView.getProdutoView().setProduto(new ConsultarProdutoPorProdutoAntesIdTask(produtoDAO, produto.getId()).execute().get());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return caixaProdutoOpView;
    }

    private List<CaixaProdutoOpView> listaCorrigida() {
        return listaCaixaProdutoOpView.stream().filter(caixaProdutoView -> caixaProdutoView.getProdutoView().getProduto().getQtd() >= caixaProdutoView.getCaixaProduto().getQtd()).collect(Collectors.toList());
    }
}
