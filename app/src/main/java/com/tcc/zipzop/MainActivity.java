package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tcc.zipzop.activity.ProdutoActivity;

public class MainActivity extends AppCompatActivity {
    private Button bt_Produto;
    private Button bt_Caixa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent intent = new Intent(MainActivity.this,CaixaActivity.class);
                startActivity(intent);
            }

        });

    }
}