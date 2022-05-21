package com.tcc.zipzop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tcc.zipzop.R;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

public class EditarItemActivity extends AppCompatActivity {
    private ItemDAO dao;
    Item itens;
    private EditText  campoNome,campoCustoProducao,campoPrecoVenda,campoQuantidade;
    private AppCompatButton bt_Salvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_editar_item);


        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getItemDAO();
        Intent intent = getIntent();
        Long id = intent.getLongExtra("id",0);
        itens = dao.consultar(id);
        Log.i("teste", String.valueOf(itens.getNome()));
        inicializaCampos();
        preencheCampos();
        bt_Salvar = findViewById(R.id.Bt_Salvar);
        bt_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormulario();
                finish();


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
        campoNome.setText(itens.getNome());
        campoCustoProducao.setText(""+itens.getCusto());
        campoPrecoVenda.setText(""+itens.getPreco());
        campoQuantidade.setText(""+itens.getQtd());
    }
    private void preencheItem() {
        String nome = campoNome.getText().toString();
        String auxCustoProducao = campoCustoProducao.getText().toString();
        Float custoProducao = converteFloat(auxCustoProducao);
        String auxPrecoVenda = campoPrecoVenda.getText().toString();
        Float precoVenda = converteFloat(auxPrecoVenda);
        String auxQuantidade = campoQuantidade.getText().toString();
        Integer quantidade = Integer.parseInt(auxQuantidade);

        itens.setNome(nome);
        itens.setCusto(custoProducao);
        itens.setPreco(precoVenda);
        itens.setQtd(quantidade);
    }

    private void finalizaFormulario() {
        preencheItem();
        dao.alterar(itens);
        Log.i("Teste", String.valueOf(itens));

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
