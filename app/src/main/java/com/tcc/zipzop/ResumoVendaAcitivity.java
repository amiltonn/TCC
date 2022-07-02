package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.VendaProdutoView;
import com.tcc.zipzop.view.VendaView;

import java.util.List;

public class ResumoVendaAcitivity extends AppCompatActivity {
    private VendaView vendaView;
    private List<VendaProdutoView> vendaProdutoViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_resumo_venda_acitivity);

        final Object vendaViewReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("vendaViewValue")).getData();
        vendaView= (VendaView) vendaViewReceived;
        Log.d("Resumo", String.valueOf(vendaView));

        resumoProdutoVendaListView();


    }

    private void resumoProdutoVendaListView() {
        ListView resumoVendaProdutoList = findViewById(R.id.resumoVendaProdutoList);
        vendaProdutoViewList = vendaView.getVendaProdutoViewList();
        ProdutoVendaAdapterActivity produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(this,vendaProdutoViewList);
        resumoVendaProdutoList.setAdapter(produtoVendaAdapterActivity);
    }
}