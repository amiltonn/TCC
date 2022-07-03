package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tcc.zipzop.adapter.VendaAdapterAcitivity;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.operations.VendaOpView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VendaActivity extends AppCompatActivity {
    private Button buttonNovaVenda;
    private VendaOpView vendaOpView;
    private ListView vendaListView;
    //banco
    VendaDAO vendaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_venda);
        //banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        vendaDAO = dataBase.getVendaDAO();
        popularVendaList();
        //Função do botão para ir para tela de nova venda
        buttonNovaVenda = findViewById(R.id.bt_NovaVenda);
        buttonNovaVenda.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendaActivity.this, NovaVendaActivity.class);
                startActivity(intent);

            }
        });


    }

    private void popularVendaList() {
        List<Venda> vendaList = new ArrayList<>();
        try {
            vendaList = new ListarVendaTask(vendaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vendaListView = findViewById(R.id.vendalist);
        VendaAdapterAcitivity vendaAdapterAcitivity = new VendaAdapterAcitivity(this,vendaList);
        vendaListView.setAdapter(vendaAdapterAcitivity);
    }
}