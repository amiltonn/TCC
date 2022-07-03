package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.tcc.zipzop.view.operations.CaixaProdutoOpView;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.view.operations.ProdutoOpView;

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
    private List<CaixaProdutoOpView> listaCaixaProdutoOpView;
    private ProdutoCaixaAdapterActivity produtoCaixaAdapterActivity;

    private Spinner spinnerProdutos;
    List<ProdutoOpView> produtoOpViewList;
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

        produtoOpViewList = new ArrayList<>();
        try {
            new ListarProdutoTask(produtoDAO).execute().get().forEach(this::populaProdutoView);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.spinnerProdutos = (Spinner) this.findViewById(R.id.SpnProduto);
        ArrayAdapter<ProdutoOpView> spnProdutoAdapter = new ArrayAdapter<ProdutoOpView>(this, android.R.layout.simple_dropdown_item_1line, this.produtoOpViewList);
        this.spinnerProdutos.setAdapter(spnProdutoAdapter);
        //end spinner

        this.quantidadeProdutos = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos produtos do caixa
        this.listViewProdutos = (ListView) this.findViewById(R.id.lsvProdutos);
        this.listaCaixaProdutoOpView = new ArrayList<>();
        this.produtoCaixaAdapterActivity = new ProdutoCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaCaixaProdutoOpView);
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

    public void corrigirSpinner(List<CaixaProdutoOpView> listaCPV) {
        this.produtoCaixaAdapterActivity.atualizar(listaCPV);
    }

    private void populaProdutoView(Produto produto) {
        ProdutoOpView pv = new ProdutoOpView();
        pv.setProduto(produto);
        produtoOpViewList.add(pv);
    }

    public void eventAddProduto(View view) {
        CaixaProdutoOpView caixaProdutoOpView = new CaixaProdutoOpView();

        ProdutoOpView produtoSelecionado = (ProdutoOpView) this.spinnerProdutos.getSelectedItem();
        if (produtoSelecionado != null) {
            if (!listaCaixaProdutoOpView.stream().map(CaixaProdutoOpView::getProdutoView)
                    .collect(Collectors.toList()).contains(produtoSelecionado)) {
                int quantidadeProduto = 0;
                if (this.quantidadeProdutos.getText().toString().equals("")) {
                    quantidadeProduto = 1;
                } else {
                    quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
                }
                caixaProdutoOpView.setProdutoView(produtoSelecionado);
                CaixaProduto cp = new CaixaProduto();
                cp.setQtd(quantidadeProduto);
                caixaProdutoOpView.setCaixaProduto(cp);

                if (caixaProdutoOpView.getProdutoView().getProduto().getQtd() >= caixaProdutoOpView.getCaixaProduto().getQtd())
                    this.produtoCaixaAdapterActivity.addProdutoCaixa(caixaProdutoOpView);
                else
                    Toast.makeText(this, "Quantidade de Produtos do Caixa passou a de Produtos!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Produto já Selecionado no Caixa!", Toast.LENGTH_LONG).show();
            }
        }
        quantidadeProdutos.setText("");
    }

    public void abrirCaixa(){
        caixaFundo = new CaixaFundo();
        campoFundoCaixa = findViewById(R.id.fundoCaixa);
        if (TextUtils.isEmpty(campoFundoCaixa.getText())){
            campoFundoCaixa.setError("Campo Obrigatorio!");
        }else{
            try {
                listaCaixaProdutoOpView = new SalvarCaixaActivityTask(caixaDAO, produtoDAO, listaCaixaProdutoOpView, this).execute().get();
                caixaAberto =  new ConsultarCaixaAbertoTask(caixaDAO).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void salvarCaixaFundo(){
        String auxFundoCaixa = campoFundoCaixa.getText().toString();
        Integer fundoCaixa = MoneyConverter.converteParaCentavos(auxFundoCaixa);
        caixaFundo.setValor(fundoCaixa);
        caixaFundo.setCaixaId(caixaAberto.getId());
        new SalvarCaixaFundoActivityTask(caixaFundoDAO,caixaFundo,this).execute();


    }

    public void salvarCaixaProduto(){
        listaCaixaProdutoOpView.forEach(caixaPView-> {
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