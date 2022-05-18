package com.tcc.zipzop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
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
    List<Item> itens;
    private FloatingActionButton floatingActionButtonNovoItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_item);
        //listagem dos itens
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        itens = dao.listar();
        lista = findViewById(R.id.Listar_Item);
        itemAdapterActivity = new ItemAdapterActivity(itens,this);
        lista.setAdapter(itemAdapterActivity);
        lista.setLayoutManager(new LinearLayoutManager(this));
        //Função do botão
        floatingActionButtonNovoItem = findViewById(R.id.floatingActionButtonNovoItem);
        floatingActionButtonNovoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this,NovoItemActivity.class);

                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int posicao = ((itemAdapterActivity.getPosicao()));
        Long id = ((itemAdapterActivity.getId()));

        switch (item.getItemId()){
            case R.id.excluir:
                break;
            case R.id.editar:
                Intent intent = new Intent(ItemActivity.this,EditarItemActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }


}