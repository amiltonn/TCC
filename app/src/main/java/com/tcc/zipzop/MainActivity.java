package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tcc.zipzop.activity.ItemActivity;

public class MainActivity extends AppCompatActivity {
    private Button bt_Item;
    private Button bt_Caixa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_Item = findViewById(R.id.buttonItem);
        bt_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
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