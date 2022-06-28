package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tcc.zipzop.activity.ProdutoActivity;
import com.tcc.zipzop.asynctask.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.BuscarCaixaFundoPeloCaixaAbertoTask;
import com.tcc.zipzop.asynctask.FecharCaixaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;

import java.util.concurrent.ExecutionException;

public class CaixaAbertoActivity extends AppCompatActivity {
     TextView campoDataAbrirCaixa, troco;
     AppCompatButton btfecharCaixa;

    private Caixa caixa;
    private CaixaFundo caixaFundo;
    private CaixaDAO caixaDAO;
    private CaixaFundoDAO caixaFundoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_caixa_aberto);
        //Config Banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        caixaDAO = dataBase.getCaixaDAO();
        caixaFundoDAO = dataBase.getCaixaFundoDAO();
        //----------------------------------------//
        prenchercampoData();
        prenchercampoTroco();
        preencherListaProduto();
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
    private void preencherListaProduto() {
    }

    private void fecharCaixa() {
        new FecharCaixaTask(caixaDAO).execute();
        finish();
        Intent intent = new Intent(CaixaAbertoActivity.this, ProdutoActivity.class);
        startActivity(intent);
    }

}