package com.tcc.zipzop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.adapter.ProdutoAdapterActivity;
import com.tcc.zipzop.asynctask.ExcluirProdutoTask;
import com.tcc.zipzop.asynctask.ListarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProdutoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProdutoAdapterActivity produtoAdapterActivity;
    private ProdutoDAO dao;
    List<Produto> produtos;
    private FloatingActionButton floatingActionButtonNovoProduto;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_produto);
        //listagem dos produtos
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getProdutoDAO();
        try {
            produtos = new ListarProdutoTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.ListarProduto);
        produtoAdapterActivity = new ProdutoAdapterActivity(produtos,this);
        recyclerView.setAdapter(produtoAdapterActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Função do botão
        floatingActionButtonNovoProduto = findViewById(R.id.floatingActionButtonNovoProduto);
        floatingActionButtonNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProdutoActivity.this, SalvarProdutoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            produtos = new ListarProdutoTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        produtoAdapterActivity.atualiza(produtos);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {

        Integer id = produtoAdapterActivity.getId();
        int posicao = ((produtoAdapterActivity.getPosicao()));
        switch (menuItem.getItemId()){
            case R.id.excluir:
                Produto produto = produtoAdapterActivity.getProduto(posicao);
                new ExcluirProdutoTask(dao, produtoAdapterActivity, produto).execute();
                break;
            case R.id.editar:
                intent = new Intent(this, SalvarProdutoActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(menuItem);
    }


}