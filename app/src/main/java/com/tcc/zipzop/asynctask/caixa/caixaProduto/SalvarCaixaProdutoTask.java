package com.tcc.zipzop.asynctask.caixa.caixaProduto;

import android.os.AsyncTask;

import com.tcc.zipzop.AbrirCaixaActivity;
import com.tcc.zipzop.asynctask.ValidarQuantidades;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;

import java.util.concurrent.ExecutionException;

public class SalvarCaixaProdutoTask extends AsyncTask<Void, Void, Void> {
    private final CaixaProdutoDAO dao;
    private final AbrirCaixaActivity abrirCaixaActivity;
    private final CaixaProduto caixaProduto;

    public SalvarCaixaProdutoTask(CaixaProdutoDAO dao, AbrirCaixaActivity abrirCaixaActivity, CaixaProduto caixaProduto){
        this.dao = dao;
        this.abrirCaixaActivity = abrirCaixaActivity;
        this.caixaProduto = caixaProduto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        alterarQtdProduto();
        dao.salvar(caixaProduto);
        return null;
    }

    private void alterarQtdProduto() {
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(abrirCaixaActivity);
        ProdutoDAO produtoDAO = dataBase.getProdutoDAO();
        Produto produto = new Produto();
        try {
            produto = new ConsultarProdutoTask(produtoDAO, caixaProduto.getProdutoId()).get();
            if(new ValidarQuantidades(produto.getQtd(), caixaProduto.getQtd()).get().equals(false)) {
                throw new Exception("Quantidade de Produtos do Caixa passou Produtos em estoque!");
            } else {
                produto.setQtd(produto.getQtd() - caixaProduto.getQtd());
                produtoDAO.alterar(produto);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
