package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.asynctask.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.view.CaixaProdutoView;
import com.tcc.zipzop.entity.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@RequiresApi(api = Build.VERSION_CODES.N)
public class NovaVendaActivity extends AppCompatActivity {
    private AppCompatButton ButtonNovaVenda;
    private EditText quantidadeProdutos;

    //Produtos do CaixaProduto
    private ListView listarProdutos;
    private List<CaixaProdutoView> listaProdutosDaVenda;
    private ProdutoVendaAdapterActivity produtoVendaAdapterActivity;
    //Spinner
    private Spinner spinnerCaixaproduto;
    List<CaixaProduto> listaCaixaProdutoSpinner;
    List<Produto> listaProdutos;
    //banco
    private CaixaDAO caixaDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private ProdutoDAO produtoDAO;
    //entity
    private Caixa caixa;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_nova_venda);
        //banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        caixaDAO = dataBase.getCaixaDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        produtoDAO = dataBase.getProdutoDAO();
        //spinner
        popularSpinner();


        this.quantidadeProdutos = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos produtos do caixa
        this.listarProdutos = (ListView) this.findViewById(R.id.listVendaProduto);
        this.listaProdutosDaVenda = new ArrayList<>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.listaProdutosDaVenda);
        this.listarProdutos.setAdapter(this.produtoVendaAdapterActivity);

        //Função do botão
        ButtonNovaVenda = findViewById(R.id.Bt_Vender);
        ButtonNovaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovaVendaActivity.this,CaixaActivity.class);

                startActivity(intent);

            }
        });

    }


    private void popularSpinner() {
        try {
            caixa=  new BuscarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            listaCaixaProdutoSpinner = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO,caixa.getId()).execute().get();
            Log.d("teste", String.valueOf(listaCaixaProdutoSpinner));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listaProdutos = new ArrayList<>();
        listaCaixaProdutoSpinner.forEach(produtoPView ->{
            try {
                produto = new ConsultarProdutoTask(produtoDAO,produtoPView.getProdutoId()).execute().get();
                Log.d("teste", String.valueOf(produto));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listaProdutos.add(produto);

        });
        spinnerCaixaproduto = findViewById(R.id.spinnerProdutoVenda);
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_dropdown_item_1line, this.listaProdutos);
        spinnerCaixaproduto.setAdapter(spnProdutoAdapter);
    }

    public void eventAddProduto(View view) {
//        CaixaProdutoView produtoDaVenda = new CaixaProdutoView();
//
//        Produto produtoSelecionado = (Produto) this.spinnerProdutos.getSelectedItem();
//
//        int quantidadeProduto = 0;
//        if(this.quantidadeProdutos.getText().toString().equals("")){
//            quantidadeProduto = 1;
//        }else {
//            quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
//        }
//
//        produtoDaVenda.setNome(produtoSelecionado.getNome());
//        produtoDaVenda.setQtdSelecionada(quantidadeProduto);
//
//        this.produtoVendaAdapterActivity.addProdutoVenda(produtoDaVenda);

    }


}