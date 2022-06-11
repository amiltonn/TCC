package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VendaActivity extends AppCompatActivity {
    private Button ButtonNovaVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_venda);


        //Função do botão para ir para tela de nova venda
        ButtonNovaVenda = findViewById(R.id.bt_NovaVenda);
        ButtonNovaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendaActivity.this,NovaVendaActivity.class);

                startActivity(intent);

            }
        });


    }
}