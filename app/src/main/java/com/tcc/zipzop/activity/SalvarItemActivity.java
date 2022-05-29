package com.tcc.zipzop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;

import com.tcc.zipzop.R;
import com.tcc.zipzop.adapter.ItemAdapterActivity;
import com.tcc.zipzop.asynctask.ConsultarItemTask;
import com.tcc.zipzop.asynctask.EditarItemTask;
import com.tcc.zipzop.asynctask.SalvarItemTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class SalvarItemActivity extends AppCompatActivity {

    private AppCompatButton btSalvar;
    private ItemAdapterActivity adapter;
    private ItemDAO dao;
    private Item item;
    private EditText    campoNome,
                        campoCustoProducao,
                        campoPrecoVenda,
                        campoQuantidade;
    Intent intent;
    Integer id = 0;
    ItemAdapterActivity itemAdapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_salvar_item);
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();

        inicializaCampos();
        preencheCampos();

        btSalvar = findViewById(R.id.Bt_Cadastrar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormulario();
            }
        });
    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.Nome);
        campoCustoProducao = findViewById(R.id.CustoProducao);
        campoPrecoVenda = findViewById(R.id.PrecoVenda);
        campoQuantidade = findViewById(R.id.Quantidade);

    }

    private void preencheCampos() {
        this.intent = getIntent();
        id = intent.getIntExtra("id", 0);
        try {
            item = new ConsultarItemTask(dao, id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //item = dao.consultar(id);
        //novo item
        if(id.equals(0)) {
            item = new Item();
        }
        //edita o item
        else{
            campoNome.setText(item.getNome());
            campoCustoProducao.setText("" + item.getCusto());
            campoPrecoVenda.setText("" + item.getPreco());
            campoQuantidade.setText("" + item.getQtd());
        }
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
        //novo item
        if(id.equals(0)){
            new SalvarItemTask(dao, this, item).execute();
        }
        //edita o item
        else{
            new EditarItemTask(dao, this, item).execute();
        }

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

    public void salvarComSucesso(){
        finish();

    }

}
