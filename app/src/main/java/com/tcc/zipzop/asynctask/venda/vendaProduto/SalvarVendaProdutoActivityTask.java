package com.tcc.zipzop.asynctask.venda.vendaProduto;

import com.tcc.zipzop.asynctask.caixa.caixaProduto.ConsultarCaixaProdutoPorProdutoIdAndDataAlteracaoMaxTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ConsultarCaixaProdutoTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.EditarCaixaProdutoTask;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.VendaProduto;

import java.util.concurrent.ExecutionException;

public class SalvarVendaProdutoActivityTask extends SalvarVendaProdutoTask {

    private final CaixaProdutoDAO caixaProdutoDAO;
    protected VendaProduto vendaProduto;

    public SalvarVendaProdutoActivityTask(VendaProdutoDAO dao, VendaProduto vendaProduto, CaixaProdutoDAO caixaProdutoDAO) {
        super(dao, vendaProduto);
        this.caixaProdutoDAO = caixaProdutoDAO;
        this.vendaProduto = vendaProduto;
    }

    @Override
    protected void onPreExecute() {
        alterarQtdCaixaProduto();
    }

    private void alterarQtdCaixaProduto() {
        try {
            CaixaProduto caixaProduto = new ConsultarCaixaProdutoTask(caixaProdutoDAO, vendaProduto.getCaixaProdutoId()).execute().get();
            if(caixaProduto.getQtd() < vendaProduto.getQtd()) {
                throw new Exception("Quantidade de Produtos Vendidos passou Produtos do Caixa!");
            } else {
                caixaProduto.setQtd(caixaProduto.getQtd() - vendaProduto.getQtd());
                new EditarCaixaProdutoTask(caixaProdutoDAO, caixaProduto).execute();
                caixaProduto = new ConsultarCaixaProdutoPorProdutoIdAndDataAlteracaoMaxTask(caixaProdutoDAO, caixaProduto.getProdutoId()).execute().get();
                super.vendaProduto.setCaixaProdutoId(caixaProduto.getId());
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
