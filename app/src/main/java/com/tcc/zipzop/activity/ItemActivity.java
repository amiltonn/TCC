package com.tcc.zipzop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.R;
import com.tcc.zipzop.adapter.ItemAdapterActivity;
import com.tcc.zipzop.asynctask.ExcluirItemTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.List;


public class ItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapterActivity itemAdapterActivity;
    private ItemDAO dao;
    List<Item> itens;
    private FloatingActionButton floatingActionButtonNovoItem;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_item);
        //listagem dos itens
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        itens = dao.listar();
        recyclerView = findViewById(R.id.Listar_Item);
        itemAdapterActivity = new ItemAdapterActivity(itens,this);
        recyclerView.setAdapter(itemAdapterActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Função do botão
        floatingActionButtonNovoItem = findViewById(R.id.floatingActionButtonNovoItem);
        floatingActionButtonNovoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ItemActivity.this, SalvarItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {

        Long id = itemAdapterActivity.getId();
        Item item = this.dao.consultar(id);

        switch (menuItem.getItemId()){
            case R.id.excluir:
                new ExcluirItemTask(dao, itemAdapterActivity, item).execute();
                itemAdapterActivity.excluir(item);
                startActivity(getIntent());
                break;
            case R.id.editar:
                intent = new Intent(this, SalvarItemActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(menuItem);
    }

}