package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.asynctask.ListarVendaProdutoTask;
import com.tcc.zipzop.asynctask.ListarVendaTask;
import com.tcc.zipzop.asynctask.SalvarVendaProdutoTask;
import com.tcc.zipzop.asynctask.SalvarVendaTask;
import com.tcc.zipzop.asynctask.caixa.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.asynctask.venda.formaPagamento.ListarFormaPagamentoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.FormaPagamentoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.entity.VendaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.view.VendaProdutoView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class NovaVendaActivity extends AppCompatActivity {
    private AppCompatButton ButtonNovaVenda;
    private EditText quantidadeProdutos, valorPago;
    private TextView valorTotal;
    //Produtos da VendaProduto/adapter
    private ListView listarCaixaProdutos;
    private List<VendaProdutoView> listaProdutosDaVenda;
    private ProdutoVendaAdapterActivity produtoVendaAdapterActivity;
    //Spinner
    private Spinner spinnerCaixaproduto;
    List<CaixaProduto> listaCaixaProdutos;
    List<Produto> listaProdutos;
    //banco
    private VendaDAO vendaDAO;
    private VendaProdutoDAO vendaProdutoDAO;
    private FormaPagamentoDAO formaPagamentoDAO;
    private CaixaDAO caixaDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private ProdutoDAO produtoDAO;
    //entity
    private Venda venda;
    private VendaProduto vendaProduto;
    private Caixa caixa;
    private CaixaProduto caixaProduto;
    private Produto produto;
    private VendaProdutoView vendaProdutoView;
    //formaPagamento
    private Spinner spinnerFormaPagamento;
    ArrayAdapter<FormaPagamento> formaPagamentoAdapter;
    private FormaPagamento formaPagamentoSelected;
    List<FormaPagamento> formaPagamentos;
    //List
    private List<Venda> vendaList;
    private List<VendaProduto> vendaProdutoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_nova_venda);
        //banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtoDAO = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        vendaDAO = dataBase.getVendaDAO();
        vendaProdutoDAO = dataBase.getVendaProdutoDAO();
        formaPagamentoDAO = dataBase.getFormaPagamentoDAO();
        //spinner
        popularSpinner();

        try {
            formaPagamentos = new ListarFormaPagamentoTask(formaPagamentoDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inicializaCampos();
        valorPago = findViewById(R.id.valorPago);

        // variaveis e objetos dos produtos do caixa
        this.listarCaixaProdutos = (ListView) this.findViewById(R.id.listVendaProduto);
        this.listaProdutosDaVenda = new ArrayList<>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.listaProdutosDaVenda);
        this.listarCaixaProdutos.setAdapter(this.produtoVendaAdapterActivity);


        //Função do botão
        ButtonNovaVenda = findViewById(R.id.Bt_Vender);
        ButtonNovaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarVenda();


            }
        });

    }

    private void inicializaCampos() {
        spinnerFormaPagamento = (Spinner) findViewById(R.id.listaFormaPagamento);
        spinnerFormaPagamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formaPagamentoSelected = (FormaPagamento) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                formaPagamentoSelected = null;
            }
        });
        formaPagamentoAdapter = new ArrayAdapter<FormaPagamento>(getBaseContext(),
            android.R.layout.simple_dropdown_item_1line, this.formaPagamentos);
        spinnerFormaPagamento.setAdapter(formaPagamentoAdapter);

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
            listaCaixaProdutos = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO,caixa.getId()).execute().get();
            Log.d("CaixaProduto", String.valueOf(listaCaixaProdutos));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listaProdutos = new ArrayList<>();
        listaCaixaProdutos.forEach(produtoPView ->{
            try {
                produto = new ConsultarProdutoTask(produtoDAO,produtoPView.getProdutoId()).execute().get();
                produto.setQtd(produtoPView.getQtd());
                Log.d("produtosdoCaixaProduto", String.valueOf(produto));
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
        vendaProdutoView = new VendaProdutoView();
        quantidadeProdutos = findViewById(R.id.Quantidade);
        Produto produtoSelecionado = (Produto) spinnerCaixaproduto.getSelectedItem();
        if (produtoSelecionado != null && !listaProdutosDaVenda.stream().map(prodVenda -> prodVenda.getNome())
                .collect(Collectors.toList()).contains(produtoSelecionado.getNome())){
            int quantidadeProduto = 0;
            if(this.quantidadeProdutos.getText().toString().equals("")) {
                quantidadeProduto = 1;

            }else {
                quantidadeProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }
            vendaProdutoView.setProdutoId(produtoSelecionado.getId());
            vendaProdutoView.setCaixaProdutoId(listaCaixaProdutos.stream().filter(cProduto -> cProduto.getProdutoId()
                            .equals(produtoSelecionado.getId())).findFirst().get().getProdutoId());
            vendaProdutoView.setNome(produtoSelecionado.getNome());
            vendaProdutoView.setPreco(produtoSelecionado.getPreco());
            vendaProdutoView.setQtdSelecionada(quantidadeProduto);
            vendaProdutoView.setQtdCaixa(produtoSelecionado.getQtd());
            vendaProdutoView.setPrecoVenda(produtoSelecionado.getPreco() * quantidadeProduto);
            produtoVendaAdapterActivity.addProdutoVenda(vendaProdutoView);
        }
        preencherValorTotal();
        Log.d("ListaProdutodaVenda", String.valueOf(listaProdutosDaVenda));
    }

    private void preencherValorTotal() {
        valorTotal = findViewById(R.id.valorTotal);
        Integer somaValorTotal = 0;
        for (VendaProdutoView precoPvenda : listaProdutosDaVenda) {
            somaValorTotal += precoPvenda.getPrecoVenda();
        }
        valorTotal.setText(""+somaValorTotal);
    }
    private void finalizarVenda() {
        venda = new Venda();
        if (valorPago.getText().toString().equals("")){
            venda.setValorPago(Integer.parseInt(valorTotal.getText().toString()));
        }else{
            venda.setValorPago(Integer.parseInt(valorPago.getText().toString()));
        }
        venda.setValorVenda(Integer.parseInt(valorTotal.getText().toString()));
        //venda.setVendaLocalId(1);
        venda.setFormaPagamentoId(formaPagamentoSelected.getId());
        venda.setCaixaId(caixa.getId());
        new SalvarVendaTask(vendaDAO,venda).execute();

        try {
            vendaList = new ListarVendaTask(vendaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("listavendaBanco", String.valueOf(vendaList));
        salvarVendaProduto();
    }

    private void salvarVendaProduto() {
        vendaProduto = new VendaProduto();
        listaProdutosDaVenda.forEach(vendaP ->{
            vendaProduto.setQtd(vendaP.getQtdSelecionada());
            vendaProduto.setPrecoVenda(vendaP.getPrecoVenda());
            vendaProduto.setVendaId(1);
            vendaProduto.setCaixaProdutoId(vendaP.getCaixaProdutoId());
            new SalvarVendaProdutoTask(vendaProdutoDAO,vendaProduto).execute();
        });
        try {
            vendaProdutoList = new ListarVendaProdutoTask(vendaProdutoDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("VendaPBanco", String.valueOf(vendaProdutoList));
    }


}