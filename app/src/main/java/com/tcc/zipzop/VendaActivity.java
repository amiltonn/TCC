package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tcc.zipzop.asynctask.caixa.ChecarCaixaAbertoTask;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;

import java.util.concurrent.ExecutionException;

public class VendaActivity extends AppCompatActivity {
    private Button buttonNovaVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_venda);


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
}