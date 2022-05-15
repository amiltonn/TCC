package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
=======
import android.widget.EditText;

import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;
>>>>>>> e8a52f703647ede1650999cf1fe49c388707acd0

public class NovoItemActivity extends AppCompatActivity {


    private AppCompatButton  bt_cadastrar;

    private ItemDAO dao;
    private Item item;
    private EditText campoNome;
    private EditText campoCustoProducao;
    private EditText campoPrecoVenda;
    private EditText campoQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_novo_item);
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        inicializaCampos();



        bt_cadastrar= findViewById(R.id.Bt_Cadastrar);
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovoItemActivity.this, ItemActivity.class);
                finalizaFormulario();
                finish();
                startActivity(intent);
            }
        });
    }


    private void inicializaCampos() {
        campoNome = findViewById(R.id.Nome);
        campoCustoProducao = findViewById(R.id.CustoProducao);
        campoPrecoVenda = findViewById(R.id.PrecoVenda);
        campoQuantidade = findViewById(R.id.Quantidade);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String auxCustoProducao = campoCustoProducao.getText().toString();
        Float custoProducao = Float.parseFloat(auxCustoProducao);
        String auxPrecoVenda = campoPrecoVenda.getText().toString();
        Float precoVenda = Float.parseFloat(auxPrecoVenda);
        String auxQuantidade = campoQuantidade.getText().toString();
        Integer quantidade = Integer.parseInt(auxQuantidade);

        item.setNome(nome);
        item.setCusto(custoProducao);
        item.setPreco(precoVenda);
        item.setQtd(quantidade);
    }

    private void finalizaFormulario() {
        preencheAluno();
        dao.salvar(item);
    }

}