package com.tcc.zipzop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tcc.zipzop.adapter.ProdutoAdapterActivity;
import com.tcc.zipzop.asynctask.caixa.ChecarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.produto.ExcluirProdutoActivityTask;
import com.tcc.zipzop.asynctask.produto.ListarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapterActivity produtoAdapterActivity;
    private ProdutoDAO dao;
    private CaixaDAO caixaDAO;
    private List<Produto> produtos;
    private FloatingActionButton buttonNovoProduto;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_produto);
        //listagem dos produtos
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();
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
        Boolean existeCaixaAberto = false;
        try {
            existeCaixaAberto = new ChecarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buttonNovoProduto = findViewById(R.id.floatingActionButtonNovoProduto);
        if(existeCaixaAberto) {
            buttonNovoProduto.setVisibility(View.GONE);
            Toast.makeText(this, "Não é possível adicionar produtos com um caixa aberto!", Toast.LENGTH_SHORT).show();
        } else {
            buttonNovoProduto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(ProdutoActivity.this, SalvarProdutoActivity.class);
                    startActivity(intent);
                }
            });
        }

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
        int posicao = produtoAdapterActivity.getPosicao();
        switch (menuItem.getItemId()){
            case R.id.excluir:
                Produto produto = produtoAdapterActivity.getProduto(posicao);
                new ExcluirProdutoActivityTask(dao, produtoAdapterActivity, produto).execute();
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