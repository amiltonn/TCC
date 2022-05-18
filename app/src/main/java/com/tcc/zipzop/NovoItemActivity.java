package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.MaskFilterSpan;
import android.util.Log;
import android.view.View;

import android.widget.EditText;

import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;


public class NovoItemActivity extends AppCompatActivity {


    private AppCompatButton  bt_cadastrar;

    private ItemDAO dao;
    private Item item = new Item();
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
                Intent intent = new Intent(NovoItemActivity.this,ItemActivity.class);
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

    private void preencheItem() {
        String nome = campoNome.getText().toString();
        String auxCustoProducao = campoCustoProducao.getText().toString();
        Float custoProducao = converteFloat(auxCustoProducao);
        String auxPrecoVenda = campoPrecoVenda.getText().toString();
        Float precoVenda = converteFloat(auxPrecoVenda);
        String auxQuantidade = campoQuantidade.getText().toString();
        Integer quantidade = Integer.parseInt(auxQuantidade);

        item.setNome(nome);
        item.setCusto(custoProducao);
        item.setPreco(precoVenda);
        item.setQtd(quantidade);
    }

    private void finalizaFormulario() {
        preencheItem();
        dao.salvar(item);

    }
    public Float converteFloat(String valor){
        String retorno = new String();
        int tamanhoString = valor.length();
        for(int i = 0; i < tamanhoString; i++){
            if (valor.charAt(i) == ',') {
                retorno += '.';
            }else {
                retorno += valor.charAt(i);
            }
        }
        return Float.parseFloat(retorno);
    }



}