package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tcc.zipzop.asynctask.caixa.ChecarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.caixa.FecharCaixaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button bt_Produto;
    private Button bt_Caixa;
    private Button bt_Venda;
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
}