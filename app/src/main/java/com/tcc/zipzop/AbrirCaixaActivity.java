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
import com.tcc.zipzop.asynctask.ListarCaixaFundoTask;
import com.tcc.zipzop.asynctask.ListarCaixaProdutoTask;
import com.tcc.zipzop.asynctask.ListarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.ListarProdutoTask;
import com.tcc.zipzop.asynctask.SalvarCaixaFundoTask;
import com.tcc.zipzop.asynctask.SalvarCaixaProdutoTask;
import com.tcc.zipzop.asynctask.SalvarCaixaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.view.CaixaProdutoView;
import com.tcc.zipzop.entity.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
@RequiresApi(api = Build.VERSION_CODES.N)
public class AbrirCaixaActivity extends AppCompatActivity {
    private AppCompatButton ButtonAbrirCaixa;
    private EditText quantidadeProdutos, campoFundoCaixa;

    //Produtos do Caixa
    private ListView listarProdutos;
    private List<CaixaProdutoView> listaCaixaProdutoView;
    private ProdutoCaixaAdapterActivity produtoCaixaAdapterActivity;

    private Spinner spinnerProdutos;
    List<Produto> listaProdutos;
    private ProdutoDAO produtosCtrl;
    //Abrir Caixa
    private CaixaDAO caixaDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private Caixa caixaAberto ;
    private CaixaFundo caixaFundo;
    private CaixaProduto caixaProduto;
    private List<CaixaFundo> listaCaixaFundo;
    private List<CaixaProduto> listaCaixaProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_abrir_caixa);

        //spinner
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtosCtrl = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();
        caixaFundoDAO = dataBase.getCaixaFundoDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();


        try {
            listaProdutos = new ListarProdutoTask(produtosCtrl).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.spinnerProdutos = (Spinner) this.findViewById(R.id.spinnerProdutoVenda);
        ArrayAdapter<Produto> spnProdutoAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_dropdown_item_1line, this.listaProdutos);
        this.spinnerProdutos.setAdapter(spnProdutoAdapter);
        //end spinner

        this.quantidadeProdutos = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos produtos do caixa
        this.listarProdutos = (ListView) this.findViewById(R.id.listVendaProduto);
        this.listaCaixaProdutoView = new ArrayList<>();
        this.produtoCaixaAdapterActivity = new ProdutoCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaCaixaProdutoView);
        this.listarProdutos.setAdapter(this.produtoCaixaAdapterActivity);

        //Função do botão
        ButtonAbrirCaixa = findViewById(R.id.Bt_Vender);
        ButtonAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCaixa();
            }
        });



    }


    public void eventAddProduto(View view) {
        CaixaProdutoView produtoDoCaixa = new CaixaProdutoView();

        Produto produtoSelecionado = (Produto) this.spinnerProdutos.getSelectedItem();

        if (produtoSelecionado != null && !listaCaixaProdutoView.stream().map(prodCaixa -> prodCaixa.getNome()).collect(Collectors.toList()).contains(produtoSelecionado.getNome())){
            int quantidadeProduto = 0;
            if(this.quantidadeProdutos.getText().toString().equals("")){
                quantidadeProduto = 1;
            }else {
                quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }
            produtoDoCaixa.setId(produtoSelecionado.getId());
            produtoDoCaixa.setNome(produtoSelecionado.getNome());
            produtoDoCaixa.setQtdSelecionada(quantidadeProduto);


            this.produtoCaixaAdapterActivity.addProdutoCaixa(produtoDoCaixa);
        }
    }
    public void abrirCaixa(){
      new SalvarCaixaTask(caixaDAO,this).execute();
       try {
            caixaAberto =  new ListarCaixaAbertoTask(caixaDAO).execute().get();
           Log.d("BancodoCaixa", String.valueOf(caixaAberto));
       } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void salvarCaixaFundo(){
        caixaFundo = new CaixaFundo();
        campoFundoCaixa = findViewById(R.id.fundoCaixa);
        String auxFundoCaixa = campoFundoCaixa.getText().toString();
        Integer fundoCaixa = auxFundoCaixa != null ? Integer.parseInt(auxFundoCaixa) : 0;
        caixaFundo.setValor(fundoCaixa);
        caixaFundo.setCaixaId(caixaAberto.getId());
        new SalvarCaixaFundoTask(caixaFundoDAO,caixaFundo,this).execute();
        try {
         listaCaixaFundo=  new ListarCaixaFundoTask(caixaFundoDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void salvarCaixaProduto(){
        listaCaixaProdutoView.forEach(caixaPView-> {
            caixaProduto = new CaixaProduto();
            caixaProduto.setCaixaId(caixaAberto.getId());//TODO:Criar DAO para esse evento
            caixaProduto.setProdutoId(caixaPView.getId());
            caixaProduto.setQtd(caixaPView.getQtdSelecionada());
            new SalvarCaixaProdutoTask(caixaProdutoDAO,caixaProduto).execute();
        });
        try {
            listaCaixaProduto=  new ListarCaixaProdutoTask(caixaProdutoDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        abrirCaixaSucesso();
    }
    public  void abrirCaixaSucesso(){
        finish();
        Intent intent = new Intent(AbrirCaixaActivity.this,MainActivity.class);
        startActivity(intent);
    }
}