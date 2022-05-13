package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButtonNovoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_item);


        floatingActionButtonNovoItem = findViewById(R.id.floatingActionButtonNovoItem);
        floatingActionButtonNovoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this,NovoItemActivity.class);

                startActivity(intent);

            }
        });
    }


}