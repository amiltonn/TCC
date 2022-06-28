package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.asynctask.ListarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.view.CaixaProdutoView;
import com.tcc.zipzop.entity.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NovaVendaActivity extends AppCompatActivity {
    private AppCompatButton ButtonNovaVenda;
    private EditText quantidadeProdutos;

    //Produtos do Caixa
    private ListView listarProdutos;
    private List<CaixaProdutoView> listaProdutosDaVenda;
    private ProdutoVendaAdapterActivity produtoVendaAdapterActivity;

    private Spinner spinnerProdutos;
    List<Produto> listaProdutos;
    private ProdutoDAO produtosCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_nova_venda);

        //spinner
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtosCtrl = dataBase.getProdutoDAO();

        try {
            listaProdutos = new ListarProdutoTask(produtosCtrl).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.spinnerProdutos = (Spinner) this.findViewById(R.id.SpnProduto);
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_dropdown_item_1line, this.listaProdutos);
        this.spinnerProdutos.setAdapter(spnProdutoAdapter);
        //end spinner

        this.quantidadeProdutos = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos produtos do caixa
        this.listarProdutos = (ListView) this.findViewById(R.id.lsvProdutos);
        this.listaProdutosDaVenda = new ArrayList<>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.listaProdutosDaVenda);
        this.listarProdutos.setAdapter(this.produtoVendaAdapterActivity);

        //Função do botão
        ButtonNovaVenda = findViewById(R.id.Bt_NovaVenda);
        ButtonNovaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NovaVendaActivity.this,CaixaActivity.class);

                startActivity(intent);

            }
        });

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