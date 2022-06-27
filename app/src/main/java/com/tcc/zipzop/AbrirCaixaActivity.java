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

import com.tcc.zipzop.adapter.ProdutoCaixaAdapterActivity;
import com.tcc.zipzop.asynctask.ListarCaixaTask;
import com.tcc.zipzop.asynctask.ListarProdutoTask;
import com.tcc.zipzop.asynctask.SalvarCaixaTask;
import com.tcc.zipzop.asynctask.SalvarProdutoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.entity.NaoEntityNomeProvisorioProdutoDoCaixa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class AbrirCaixaActivity extends AppCompatActivity {
    private AppCompatButton ButtonAbrirCaixa;
    private EditText quantidadeProdutos;

    //Produtos do Caixa
    private ListView listarProdutos;
    private List<NaoEntityNomeProvisorioProdutoDoCaixa> listaProdutosDoCaixa;
    private ProdutoCaixaAdapterActivity produtoCaixaAdapterActivity;

    private Spinner spinnerProdutos;
    List<Produto> listaProdutos;
    private ProdutoDAO produtosCtrl;
    //Abrir Caixa
    private CaixaDAO caixaDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private CaixaFundo caixaFundo;
    private CaixaProduto caixaProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_abrir_caixa);

        //spinner
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtosCtrl = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();

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
        this.listaProdutosDoCaixa = new ArrayList<>();
        this.produtoCaixaAdapterActivity = new ProdutoCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaProdutosDoCaixa);
        this.listarProdutos.setAdapter(this.produtoCaixaAdapterActivity);

        //Função do botão
        ButtonAbrirCaixa = findViewById(R.id.Bt_AbrirCaixa);
        ButtonAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCaixa();
                Intent intent = new Intent(AbrirCaixaActivity.this,CaixaActivity.class);

                startActivity(intent);

            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void eventAddProduto(View view) {
        NaoEntityNomeProvisorioProdutoDoCaixa produtoDoCaixa = new NaoEntityNomeProvisorioProdutoDoCaixa();

        Produto produtoSelecionado = (Produto) this.spinnerProdutos.getSelectedItem();

        if (produtoSelecionado != null && !listaProdutosDoCaixa.stream().map(prodCaixa -> prodCaixa.getNome()).collect(Collectors.toList()).contains(produtoSelecionado.getNome())){
            int quantidadeProduto = 0;
            if(this.quantidadeProdutos.getText().toString().equals("")){
                quantidadeProduto = 1;
            }else {
                quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }
            produtoDoCaixa.setNome(produtoSelecionado.getNome());
            produtoDoCaixa.setQtdSelecionada(quantidadeProduto);

            this.produtoCaixaAdapterActivity.addProdutoCaixa(produtoDoCaixa);
        }
    }
    public void abrirCaixa(){
        new SalvarCaixaTask(caixaDAO).execute();
        try {
            List<Caixa> caixa =  new ListarCaixaTask(caixaDAO).execute().get();
            Log.i("teste", String.valueOf(caixa));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}