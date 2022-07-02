package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class CaixaActivity extends AppCompatActivity {
    private AppCompatButton buttonNovoCaixa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_caixa);


        //Função do botão
        buttonNovoCaixa = findViewById(R.id.Bt_NovoCaixa);
        buttonNovoCaixa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaixaActivity.this, AbrirCaixaActivity.class);

                startActivity(intent);

            }
        });

    }
}