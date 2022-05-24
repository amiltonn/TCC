package com.tcc.zipzop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.R;
import com.tcc.zipzop.adapter.ItemAdapterActivity;
import com.tcc.zipzop.asynctask.ConsultarItemTask;
import com.tcc.zipzop.asynctask.ExcluirItemTask;
import com.tcc.zipzop.asynctask.ListarItemTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.List;
import java.util.concurrent.ExecutionException;


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
        try {
            itens = new ListarItemTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void atualizaItens() throws ExecutionException, InterruptedException {
        itens = new ListarItemTask(dao).execute().get();
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            atualizaItens();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        itemAdapterActivity.atualiza(itens);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {

        Long id = itemAdapterActivity.getId();
        Item item = new Item();
        try {
            item = new ConsultarItemTask(dao, id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int posicao = ((itemAdapterActivity.getPosicao()));
        //item = dao.consultar(id);
        switch (menuItem.getItemId()){
            case R.id.excluir:
                Item items = itemAdapterActivity.getItem(posicao);
                new ExcluirItemTask(dao, itemAdapterActivity, items).execute();
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