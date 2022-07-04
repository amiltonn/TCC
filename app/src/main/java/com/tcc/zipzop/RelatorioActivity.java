package com.tcc.zipzop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.tcc.zipzop.asynctask.caixa.ListarCaixaTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.ConsultarVendaViewTask;
import com.tcc.zipzop.asynctask.relatorio.vendaView.caixaView.ConsultarCaixaViewTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.analytics.CaixaView;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RelatorioActivity extends AppCompatActivity {
    private AppCompatButton buttonListarVendas;
    private AppCompatButton buttonListarCaixas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_relatorio);


        //Função do botão
        buttonListarVendas = findViewById(R.id.Bt_ListarVendas);
        buttonListarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RelatorioActivity.this, RelatorioListaVendaActivity.class);
                startActivity(intent);
            }
        });

        buttonListarCaixas = findViewById(R.id.Bt_ListarCaixas);
        buttonListarCaixas.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                try {
                    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(RelatorioActivity.this);
                    List<Caixa> caixaList = new ListarCaixaTask(dataBase.getCaixaDAO()).execute().get();
                    List<CaixaView> caixaViewList = new ArrayList<>();
                    caixaList.forEach(caixa -> {
                        try {
                            caixaViewList.add(new ConsultarCaixaViewTask(RelatorioActivity.this, caixa.getId()).execute().get());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    List treco = caixaViewList;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent intent = new Intent(RelatorioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}