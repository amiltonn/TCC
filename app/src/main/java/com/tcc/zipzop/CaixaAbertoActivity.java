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
import com.tcc.zipzop.asynctask.caixa.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.caixa.caixaFundo.ConsultarCaixaFundoPeloCaixaIdTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.caixa.FecharCaixaActivityTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.typeconverter.DateTimeConverter;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.view.operations.CaixaProdutoOpView;
import com.tcc.zipzop.view.operations.ProdutoOpView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CaixaAbertoActivity extends AppCompatActivity {
    private TextView campoDataAbrirCaixa, troco;
    private AppCompatButton buttonFecharCaixa;
    private ListView listViewProdutoCaixaAberto;
    //listas
    private List<CaixaProduto> listaCaixaProduto;
    private List<CaixaProdutoOpView> listaCaixaProdutoOpView = new ArrayList<>();
    //adapter
    private ProdutoCaixaAbertoAdapterActivity produtoCaixaAbertoAdapterActivity;
    //banco / entity
    private Caixa caixa;
    private CaixaFundo caixaFundo;
    private CaixaDAO caixaDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private ProdutoDAO produtoDAO;
    private CaixaProdutoOpView caixaProdutoOpView;
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
       buttonFecharCaixa = findViewById(R.id.btfecharCaixa);
       buttonFecharCaixa.setOnClickListener(new View.OnClickListener() {
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
        campoDataAbrirCaixa.setText(""+ DateTimeConverter.dataFormatada(caixa.getDataAbertura()));
    }

    private void prenchercampoTroco() {
        try {
          caixaFundo = new ConsultarCaixaFundoPeloCaixaIdTask(caixaFundoDAO,caixa.getId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        troco = findViewById(R.id.troco);
        troco.setText(""+ MoneyConverter.toString(caixaFundo.getValor()));
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
        listaCaixaProduto.forEach(caixaProduto->{
            caixaProdutoOpView = new CaixaProdutoOpView();
            ProdutoOpView produtoOpView = new ProdutoOpView();
            Produto produto = new Produto();
            try {
                produto = new ConsultarProdutoTask(produtoDAO, caixaProduto.getProdutoId()).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("produto teste wii", String.valueOf(produto.getId()));

            produtoOpView.setProduto(produto);
            caixaProdutoOpView.setProdutoView(produtoOpView);
            caixaProdutoOpView.setCaixaProduto(caixaProduto);
            listaCaixaProdutoOpView.add(caixaProdutoOpView);
        });
        listViewProdutoCaixaAberto = findViewById(R.id.listaProdutoCaixaAberto);
        produtoCaixaAbertoAdapterActivity =  new ProdutoCaixaAbertoAdapterActivity(CaixaAbertoActivity.this, listaCaixaProdutoOpView);
        listViewProdutoCaixaAberto.setAdapter(produtoCaixaAbertoAdapterActivity);
    }

    private void fecharCaixa() {
        new FecharCaixaActivityTask(caixaDAO, caixaProdutoDAO, produtoDAO, this).execute();
    }

    public void fecharCaixaSucesso() {
        finish();
        Intent intent = new Intent(CaixaAbertoActivity.this, MainActivity.class);
        startActivity(intent);
    }

}