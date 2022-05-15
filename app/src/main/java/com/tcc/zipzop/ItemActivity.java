package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    List<Item> itens;
    RecyclerView lista;
    ItemAdapterActivity itemAdapterActivity;
    ZipZopDataBase zipZopDataBase;
    private FloatingActionButton floatingActionButtonNovoItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_item);

        lista = findViewById(R.id.Listar_Item);
        lista.setLayoutManager(new LinearLayoutManager(this));
        itens = new ArrayList<>();
        //zipZopDataBase.getItemDAO().
        //config adapter
        itemAdapterActivity = new ItemAdapterActivity(itens);
        lista.setAdapter(itemAdapterActivity);

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