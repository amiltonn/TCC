package com.tcc.zipzop.asynctask.venda;

import android.os.AsyncTask;

import com.tcc.zipzop.NovaVendaActivity;
import com.tcc.zipzop.asynctask.ValidarQuantidades;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ConsultarCaixaProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.concurrent.ExecutionException;

public class SalvarVendaProdutoTask extends AsyncTask<Void, Void, Void > {
    private final VendaProdutoDAO dao;
    private final NovaVendaActivity novaVendaActivity;
    private final VendaProduto vendaProduto;

    public SalvarVendaProdutoTask(VendaProdutoDAO dao, NovaVendaActivity novaVendaActivity, VendaProduto vendaProduto) {
        this.dao = dao;
        this.novaVendaActivity = novaVendaActivity;
        this.vendaProduto = vendaProduto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        alterarQtdCaixaProduto();
        dao.salvar(vendaProduto);
        return null;
    }

    private void alterarQtdCaixaProduto() {
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(novaVendaActivity);
        CaixaProdutoDAO caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        CaixaProduto caixaProduto = new CaixaProduto();
        try {
            caixaProduto = new ConsultarCaixaProdutoTask(caixaProdutoDAO, vendaProduto.getCaixaProdutoId()).get();
            if(new ValidarQuantidades(caixaProduto.getQtd(), vendaProduto.getQtd()).get().equals(false)) {
                throw new Exception("Quantidade de Produtos da Venda passou Produtos do Caixa!");
            } else {
                caixaProduto.setQtd(caixaProduto.getQtd() - vendaProduto.getQtd());
                caixaProdutoDAO.alterar(caixaProduto);
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
