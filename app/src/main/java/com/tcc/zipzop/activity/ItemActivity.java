package com.tcc.zipzop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


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
                Intent intent = new Intent(ItemActivity.this, NovoItemActivity.class);

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
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
                Item itemEscolhido = itemAdapterActivity.getItem(menuInfo.position);
                itemAdapterActivity.excluir(itemEscolhido);
                break;
            case R.id.editar:
                Intent intent = new Intent(this, EditarItemActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(menuItem);
    }


}