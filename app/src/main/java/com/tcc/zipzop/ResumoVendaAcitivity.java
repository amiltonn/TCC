package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.VendaProdutoView;
import com.tcc.zipzop.view.VendaView;

import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.N)

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

        resumoProdutoVendatable();


    }


    private void resumoProdutoVendatable() {
        vendaProdutoViewList = vendaView.getVendaProdutoViewList();
        TableLayout tableLayoutVendaProduto = findViewById(R.id.tableLayoutVendaProduto);
        vendaProdutoViewList.forEach(vPVenda ->{
            String [] visualizacaoVendaProduto = {vPVenda.getCaixaProdutoView().getProdutoView().getProduto().getNome(),
            String.valueOf(vPVenda.getCaixaProdutoView().getProdutoView().getProduto().getPreco()),
                    String.valueOf(vPVenda.getVendaProduto().getQtd()),
                    String.valueOf(vPVenda.getVendaProduto().getPrecoVenda())};
            Log.d("VIZUALIZAÇAO", String.valueOf(visualizacaoVendaProduto));
            TableRow row = new TableRow(getBaseContext());
            TextView textView = null;
            for (int i=0;i<4;i++) {
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setBackgroundResource(R.color.botao);
                textView.setText(visualizacaoVendaProduto[i]);
                textView.setTextColor(getResources().getColor(R.color.letra));
                row.addView(textView);
            }
            tableLayoutVendaProduto.addView(row);
        });

    }
}