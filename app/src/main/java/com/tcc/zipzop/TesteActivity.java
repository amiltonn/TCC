package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.List;

public class TesteActivity extends AppCompatActivity {
    private ItemDAO dao;
    Item itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        Intent intent = getIntent();
        Long id = intent.getLongExtra("id",0);
        itens = dao.consultar(id);
        Log.i("teste", String.valueOf(itens.getNome()));
    }
}