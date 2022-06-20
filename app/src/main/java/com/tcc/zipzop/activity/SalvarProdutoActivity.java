package com.tcc.zipzop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.tcc.zipzop.R;
import com.tcc.zipzop.adapter.ProdutoAdapterActivity;
import com.tcc.zipzop.asynctask.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.ConsultarUnidadeMedidaTask;
import com.tcc.zipzop.asynctask.EditarProdutoTask;
import com.tcc.zipzop.asynctask.ListarUnidadeMedidaTask;
import com.tcc.zipzop.asynctask.SalvarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.database.dao.UnidadeMedidaDAO;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.UnidadeMedida;
import com.tcc.zipzop.typeconverter.MoneyConverter;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SalvarProdutoActivity extends AppCompatActivity {

    private AppCompatButton btSalvar;
    private ProdutoAdapterActivity adapter;
    private ProdutoDAO dao;
    private UnidadeMedidaDAO unidadeMedidaDAO;
    private Produto produto;
    private Spinner spinnerUnidadeMedidas;
    ArrayAdapter<UnidadeMedida> unidadeMedidaAdapter;
    private UnidadeMedida unidadeMedidaSelected;
    List<UnidadeMedida> unidadeMedidas;
    private EditText    campoNome,
                        campoCustoProducao,
                        campoPrecoVenda,
                        campoQuantidade;
    Intent intent;
    Integer id = 0;
    ProdutoAdapterActivity produtoAdapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_salvar_produto);
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        dao = dataBase.getProdutoDAO();
        unidadeMedidaDAO = dataBase.getUnidadeMedidaDAO();
        try {
            unidadeMedidas = new ListarUnidadeMedidaTask(unidadeMedidaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        spinnerUnidadeMedidas = (Spinner) findViewById(R.id.listaUnidadeMedida);
        spinnerUnidadeMedidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidadeMedidaSelected = (UnidadeMedida) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                unidadeMedidaSelected = null;
            }
        });
        unidadeMedidaAdapter = new ArrayAdapter<UnidadeMedida>(getBaseContext(),
            android.R.layout.simple_dropdown_item_1line, this.unidadeMedidas);
        spinnerUnidadeMedidas.setAdapter(unidadeMedidaAdapter);
    }

    private void preencheCampos() {
        this.intent = getIntent();
        id = intent.getIntExtra("id", 0);
        try {
            produto = new ConsultarProdutoTask(dao, id).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //produto = dao.consultar(id);
        //novo produto
        if(id.equals(0)) {
            produto = new Produto();
        }
        //edita o produto
        else{
            campoNome.setText(produto.getNome());
            campoCustoProducao.setText("" + MoneyConverter.toString(produto.getCusto()));
            campoPrecoVenda.setText("" + MoneyConverter.toString(produto.getPreco()));
            campoQuantidade.setText("" + produto.getQtd());
            try {
                unidadeMedidaSelected = new ConsultarUnidadeMedidaTask(unidadeMedidaDAO, produto.getUnidadeMedidaId()).execute().get();
                spinnerUnidadeMedidas.setSelection(unidadeMedidaSelected.getId() - 1);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void preencheProduto() {
        String nome = campoNome.getText().toString();
        String auxCustoProducao = campoCustoProducao.getText().toString();
        Integer custoProducao = converteParaCentavos(auxCustoProducao);
        String auxPrecoVenda = campoPrecoVenda.getText().toString();
        Integer precoVenda = converteParaCentavos(auxPrecoVenda);
        String auxQuantidade = campoQuantidade.getText().toString();
        Integer quantidade = Integer.parseInt(auxQuantidade);

        produto.setNome(nome);
        produto.setCusto(custoProducao);
        produto.setPreco(precoVenda);
        produto.setQtd(quantidade);
        produto.setUnidadeMedidaId(unidadeMedidaSelected.getId());
    }

    private void finalizaFormulario() {
        preencheProduto();
        //novo produto
        if(id.equals(0)){
            new SalvarProdutoTask(dao, this, produto).execute();
        }
        //edita o produto
        else{
            new EditarProdutoTask(dao, this, produto).execute();
        }

    }
    public Integer converteParaCentavos(String valor){
        char penultimo = 'n';
        char antePenultimo = 'n';
        Integer retorno = null;
        if(valor.length() > 1) {
            penultimo = valor.charAt(valor.length() - 2);
            if (valor.length() > 2) {
                antePenultimo = valor.charAt(valor.length() - 3);
            }
        }

        if(antePenultimo == ',' || antePenultimo == '.')
            retorno = Integer.parseInt(valor.replaceAll("[,.]", ""));
        else if(penultimo == ',' || penultimo == '.')
            retorno = Integer.parseInt(valor.replaceAll("[,.]", "")) * 10;
        else
            retorno = Integer.parseInt(valor.replaceAll("[,.]", "")) * 100;


        return retorno;
    }

    public void salvarComSucesso(){
        finish();

    }

}
