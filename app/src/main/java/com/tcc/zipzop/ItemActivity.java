package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemActivity extends AppCompatActivity {

    RecyclerView lista;
    ItemAdapterActivity itemAdapterActivity;
    private ItemDAO dao;
    ArrayList<String> nomeItem;
    private FloatingActionButton floatingActionButtonNovoItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_item);

        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        lista = findViewById(R.id.Listar_Item);
        Cursor cursor = dao.listar();
        nomeItem = new ArrayList<>();
        while (cursor.moveToNext()){
            nomeItem.add(cursor.getString(1));
        }
        cursor.close();

        itemAdapterActivity = new ItemAdapterActivity(nomeItem,this);
        lista.setAdapter(itemAdapterActivity);
        lista.setLayoutManager(new LinearLayoutManager(this));

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