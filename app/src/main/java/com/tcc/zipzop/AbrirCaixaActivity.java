package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.zipzop.adapter.ProdutoCaixaAdapterActivity;
import com.tcc.zipzop.asynctask.caixa.ConsultarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.produto.ListarProdutoTask;
import com.tcc.zipzop.asynctask.caixa.caixaFundo.SalvarCaixaFundoTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.SalvarCaixaProdutoTask;
import com.tcc.zipzop.asynctask.caixa.SalvarCaixaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.typeconverter.MoneyConverter;
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
    private ProdutoDAO produtoDAO;
    //Abrir Caixa
    private CaixaDAO caixaDAO;
    private CaixaFundoDAO caixaFundoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private Caixa caixaAberto ;
    private CaixaFundo caixaFundo;
    private CaixaProduto caixaProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_abrir_caixa);

        //spinner
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtoDAO = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();
        caixaFundoDAO = dataBase.getCaixaFundoDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();


        try {
            listaProdutos = new ListarProdutoTask(produtoDAO).execute().get();
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
        this.listaCaixaProdutoView = new ArrayList<>();
        this.produtoCaixaAdapterActivity = new ProdutoCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaCaixaProdutoView);
        this.listarProdutos.setAdapter(this.produtoCaixaAdapterActivity);

        //Função do botão
        ButtonAbrirCaixa = findViewById(R.id.Bt_AbrirCaixa);
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

        if (produtoSelecionado != null && !listaCaixaProdutoView.stream().map(prodCaixa -> prodCaixa.getProdutoNome()).collect(Collectors.toList()).contains(produtoSelecionado.getNome())){
            int quantidadeProduto = 0;
            if(this.quantidadeProdutos.getText().toString().equals("")){
                quantidadeProduto = 1;
            }else {
                quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }
            produtoDoCaixa.setProdutoId(produtoSelecionado.getId());
            produtoDoCaixa.setProdutoNome(produtoSelecionado.getNome());
            produtoDoCaixa.setQtd(quantidadeProduto);


            this.produtoCaixaAdapterActivity.addProdutoCaixa(produtoDoCaixa);
        }
    }
    public void abrirCaixa(){
      new SalvarCaixaTask(caixaDAO,this).execute();
       try {
            caixaAberto =  new ConsultarCaixaAbertoTask(caixaDAO).execute().get();
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
        Integer fundoCaixa = auxFundoCaixa != null ? MoneyConverter.converteParaCentavos(auxFundoCaixa) : 0;
        caixaFundo.setValor(fundoCaixa);
        caixaFundo.setCaixaId(caixaAberto.getId());
        new SalvarCaixaFundoTask(caixaFundoDAO,caixaFundo,this).execute();

    }
    public void salvarCaixaProduto(){
        listaCaixaProdutoView.forEach(caixaPView-> {
            caixaProduto = new CaixaProduto();
            caixaProduto.setCaixaId(caixaAberto.getId());//TODO:Criar DAO para esse evento
            caixaProduto.setProdutoId(caixaPView.getProdutoId());
            caixaProduto.setQtd(caixaPView.getQtd());
            new SalvarCaixaProdutoTask(caixaProdutoDAO,caixaProduto).execute();
        });

        abrirCaixaSucesso();
    }
    public  void abrirCaixaSucesso(){
        finish();
        Intent intent = new Intent(AbrirCaixaActivity.this,MainActivity.class);
        startActivity(intent);
    }
}