package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.adapter.ResumoProdutoVendaAdapterActivity;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.ProdutoView;
import com.tcc.zipzop.view.VendaProdutoView;
import com.tcc.zipzop.view.VendaView;

import java.util.ArrayList;
import java.util.List;

public class ResumoVendaAcitivity extends AppCompatActivity {
    private VendaProdutoView vendaProdutoView;
    private List<VendaProdutoView> vendaProdutoViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_resumo_venda_acitivity);

        final Object vendaViewReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("vendaProdutoViewValue")).getData();
        vendaProdutoView= (VendaProdutoView) vendaViewReceived;
        Log.d("Resumo", String.valueOf(vendaProdutoView));

        resumoProdutoVendaListView();


    }

    private void resumoProdutoVendaListView() {
        ListView resumoVendaProdutoList = findViewById(R.id.resumoVendaProdutoList);
        vendaProdutoViewList = new ArrayList<>();
        vendaProdutoViewList.add(vendaProdutoView);
        ProdutoVendaAdapterActivity produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(this,vendaProdutoViewList);
        resumoVendaProdutoList.setAdapter(produtoVendaAdapterActivity);
    }
}