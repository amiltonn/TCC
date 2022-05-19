package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tcc.zipzop.entity.Caixa;

public class CaixaActivity extends AppCompatActivity {
    private AppCompatButton ButtonNovoCaixa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_caixa);


        //Função do botão
        ButtonNovoCaixa = findViewById(R.id.Bt_NovoCaixa);
        ButtonNovoCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaixaActivity.this,AbrirCaixaActivity.class);

                startActivity(intent);

            }
        });

    }
}