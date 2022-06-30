package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tcc.zipzop.adapter.ProdutoCaixaAbertoAdapterActivity;
import com.tcc.zipzop.asynctask.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.BuscarCaixaFundoPeloCaixaAbertoTask;
import com.tcc.zipzop.asynctask.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.FecharCaixaTask;
import com.tcc.zipzop.asynctask.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.view.CaixaProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CaixaAbertoActivity extends AppCompatActivity {
    private TextView campoDataAbrirCaixa, troco;
    private AppCompatButton btfecharCaixa;
    private ListView listViewProdutoCaixaAberto;
    //listas
    private List<CaixaProduto> listaCaixaProduto;
    private List<CaixaProdutoView> listaCaixaProdutoView = new ArrayList<>();
    //adapter
    private ProdutoCaixaAbertoAdapterActivity produtoCaixaAbertoAdapterActivity;
    //banco / entity
    private Caixa caixa;
    private CaixaFundo caixaFundo;
    private CaixaDAO caixaDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private ProdutoDAO produtoDAO;
    private Produto produto;
    private CaixaProdutoView caixaProdutoView;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_caixa_aberto);
        //Config Banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        caixaDAO = dataBase.getCaixaDAO();
        caixaFundoDAO = dataBase.getCaixaFundoDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        produtoDAO = dataBase.getProdutoDAO();
        //----------------------------------------//
        prenchercampoData();
        prenchercampoTroco();
        preencherListaCaixaProduto();
        //botão evento do click
       btfecharCaixa = findViewById(R.id.btfecharCaixa);
       btfecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecharCaixa();
            }

        });
    }

    private void prenchercampoData() {
        campoDataAbrirCaixa = findViewById(R.id.dataCaixaAberto);
        try {
            caixa=  new BuscarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        campoDataAbrirCaixa.setText(""+ caixa.getDataAbertura());
    }
    private void prenchercampoTroco() {
        try {
          caixaFundo = new BuscarCaixaFundoPeloCaixaAbertoTask(caixaFundoDAO,caixa.getId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        troco = findViewById(R.id.troco);
        troco.setText(""+caixaFundo.getValor());
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void preencherListaCaixaProduto() {
        try {
            listaCaixaProduto = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO,caixa.getId()).execute().get();
            Log.d("teste", String.valueOf(listaCaixaProduto));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listaCaixaProduto.forEach(caixaPView->{
            caixaProdutoView = new CaixaProdutoView();
            try {
                produto = new ConsultarProdutoTask(produtoDAO,caixaPView.getProdutoId()).execute().get();
                Log.d("teste", String.valueOf(produto));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            caixaProdutoView.setId(caixaPView.getProdutoId());
            caixaProdutoView.setNome(produto.getNome());
            caixaProdutoView.setQtdSelecionada(caixaPView.getQtd());
            listaCaixaProdutoView.add(caixaProdutoView);
        });
        listViewProdutoCaixaAberto = findViewById(R.id.listaProdutoCaixaAberto);
        produtoCaixaAbertoAdapterActivity =  new ProdutoCaixaAbertoAdapterActivity(CaixaAbertoActivity.this,listaCaixaProdutoView);
        listViewProdutoCaixaAberto.setAdapter(produtoCaixaAbertoAdapterActivity);
    }

    private void fecharCaixa() {
        new FecharCaixaTask(caixaDAO).execute();
        finish();
        Intent intent = new Intent(CaixaAbertoActivity.this, MainActivity.class);
        startActivity(intent);
    }

}