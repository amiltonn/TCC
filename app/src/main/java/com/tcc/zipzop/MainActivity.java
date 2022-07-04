package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.backup.BackupManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tcc.zipzop.asynctask.caixa.ChecarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.ConsultarVendaViewTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button bt_Produto;
    private Button bt_Caixa;
    private Button bt_Venda;
    private Button bt_Relatorio;
    private CaixaDAO caixaDAO;
    private Boolean existeCaixaAberto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        caixaDAO = dataBase.getCaixaDAO();
        // ------------------------------------//
        backupNuvem();
        bt_Produto = findViewById(R.id.buttonProduto);
        bt_Produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProdutoActivity.class);
                startActivity(intent);
            }

        });

        bt_Caixa = findViewById(R.id.buttonCaixa);
        bt_Caixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirFecharCaixa();
            }

        });

        bt_Venda = findViewById(R.id.buttonVenda);
        bt_Venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarVenda();
            }

        });

        bt_Relatorio = findViewById(R.id.buttonRelatorio);
        bt_Relatorio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RelatorioActivity.class);
                startActivity(intent);
            }
        });

    }

    public void abrirFecharCaixa(){
        try {
            existeCaixaAberto = new ChecarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (existeCaixaAberto){
            Intent intent = new Intent(MainActivity.this,CaixaAbertoActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this, CaixaActivity.class);
            startActivity(intent);
        }
    }

    public void listarVenda(){
        try {
            existeCaixaAberto = new ChecarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(existeCaixaAberto){
            Intent intent = new Intent(MainActivity.this, VendaActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nenhum caixa aberto!", Toast.LENGTH_SHORT).show();
        }
    }


    private void backupNuvem(){
        BackupManager backupManager = new BackupManager(this);
        backupManager.dataChanged();
    }
}