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
import android.widget.Toast;

import com.tcc.zipzop.adapter.ProdutoCaixaAdapterActivity;
import com.tcc.zipzop.asynctask.caixa.ConsultarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.SalvarCaixaProdutoTask;
import com.tcc.zipzop.asynctask.produto.ListarProdutoTask;
import com.tcc.zipzop.asynctask.caixa.caixaFundo.SalvarCaixaFundoActivityTask;
import com.tcc.zipzop.asynctask.caixa.SalvarCaixaActivityTask;
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
import com.tcc.zipzop.view.ProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AbrirCaixaActivity extends AppCompatActivity {
    private AppCompatButton buttonAbrirCaixa;
    private EditText quantidadeProdutos, campoFundoCaixa;

    //Produtos do Caixa
    private ListView listViewProdutos;
    private List<CaixaProdutoView> listaCaixaProdutoView;
    private ProdutoCaixaAdapterActivity produtoCaixaAdapterActivity;

    private Spinner spinnerProdutos;
    List<ProdutoView> produtoViewList;
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

        produtoViewList = new ArrayList<>();
        try {
            new ListarProdutoTask(produtoDAO).execute().get().forEach(this::populaProdutoView);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.spinnerProdutos = (Spinner) this.findViewById(R.id.SpnProduto);
        ArrayAdapter<ProdutoView> spnProdutoAdapter = new ArrayAdapter<ProdutoView>(this, android.R.layout.simple_dropdown_item_1line, this.produtoViewList);
        this.spinnerProdutos.setAdapter(spnProdutoAdapter);
        //end spinner

        this.quantidadeProdutos = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos produtos do caixa
        this.listViewProdutos = (ListView) this.findViewById(R.id.lsvProdutos);
        this.listaCaixaProdutoView = new ArrayList<>();
        this.produtoCaixaAdapterActivity = new ProdutoCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaCaixaProdutoView);
        this.listViewProdutos.setAdapter(this.produtoCaixaAdapterActivity);

        //Função do botão
        buttonAbrirCaixa = findViewById(R.id.Bt_AbrirCaixa);
        buttonAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCaixa();
            }
        });
    }

    public void corrigirSpinner(List<CaixaProdutoView> listaCPV) {
        this.produtoCaixaAdapterActivity.atualizar(listaCPV);
    }

    private void populaProdutoView(Produto produto) {
        ProdutoView pv = new ProdutoView();
        pv.setProduto(produto);
        produtoViewList.add(pv);
    }

    public void eventAddProduto(View view) {
        CaixaProdutoView caixaProdutoView = new CaixaProdutoView();

        ProdutoView produtoSelecionado = (ProdutoView) this.spinnerProdutos.getSelectedItem();
        if (produtoSelecionado != null) {
            if (!listaCaixaProdutoView.stream().map(CaixaProdutoView::getProdutoView)
                    .collect(Collectors.toList()).contains(produtoSelecionado)) {
                int quantidadeProduto = 0;
                if (this.quantidadeProdutos.getText().toString().equals("")) {
                    quantidadeProduto = 1;
                } else {
                    quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
                }
                caixaProdutoView.setProdutoView(produtoSelecionado);
                CaixaProduto cp = new CaixaProduto();
                cp.setQtd(quantidadeProduto);
                caixaProdutoView.setCaixaProduto(cp);

                if (caixaProdutoView.getProdutoView().getProduto().getQtd() >= caixaProdutoView.getCaixaProduto().getQtd())
                    this.produtoCaixaAdapterActivity.addProdutoCaixa(caixaProdutoView);
                else
                    Toast.makeText(this, "Quantidade de Produtos do Caixa passou a de Produtos!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Produto já Selecionado no Caixa!", Toast.LENGTH_LONG).show();
            }
        }
        quantidadeProdutos.setText("");
    }

    public void abrirCaixa(){
        try {
            listaCaixaProdutoView = new SalvarCaixaActivityTask(caixaDAO, produtoDAO, listaCaixaProdutoView, this).execute().get();
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
        new SalvarCaixaFundoActivityTask(caixaFundoDAO,caixaFundo,this).execute();

    }

    public void salvarCaixaProduto(){
        listaCaixaProdutoView.forEach(caixaPView-> {
            caixaProduto = new CaixaProduto();
            caixaProduto.setCaixaId(caixaAberto.getId());
            caixaProduto.setProdutoId(caixaPView.getProdutoView().getProduto().getId());
            caixaProduto.setQtd(caixaPView.getCaixaProduto().getQtd());
            new SalvarCaixaProdutoTask(caixaProdutoDAO, caixaProduto).execute();
        });

        finish();
        Intent intent = new Intent(AbrirCaixaActivity.this,MainActivity.class);
        startActivity(intent);
    }
}