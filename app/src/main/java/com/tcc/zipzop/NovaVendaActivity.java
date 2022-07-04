package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import android.widget.Toast;

import com.tcc.zipzop.adapter.ProdutoVendaAdapterActivity;
import com.tcc.zipzop.asynctask.caixa.BuscarCaixaAbertoTask;
import com.tcc.zipzop.asynctask.produto.ConsultarProdutoTask;
import com.tcc.zipzop.asynctask.caixa.caixaProduto.ListaCaixaProdutoAbertoTask;
import com.tcc.zipzop.asynctask.venda.formaPagamento.ListarFormaPagamentoTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.FormaPagamentoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Caixa;
import com.tcc.zipzop.entity.CaixaProduto;
import com.tcc.zipzop.entity.FormaPagamento;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.entity.VendaProduto;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.operations.CaixaProdutoOpView;
import com.tcc.zipzop.view.operations.ProdutoOpView;
import com.tcc.zipzop.view.operations.VendaProdutoOpView;
import com.tcc.zipzop.view.operations.VendaOpView;

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
    private RecyclerView listViewDeVendaProdutos;
    private List<VendaProdutoOpView> vendaProdutoOpViewList;
    private ProdutoVendaAdapterActivity produtoVendaAdapterActivity;

    //banco
    private FormaPagamentoDAO formaPagamentoDAO;
    private CaixaDAO caixaDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private ProdutoDAO produtoDAO;
    //entity
    private Venda venda;
    private Caixa caixa;
    private Produto produto;
    private VendaProdutoOpView vendaProdutoOpView;

    //caixaProdutoSpinner
    private Spinner spinnerCaixaproduto;
    CaixaProdutoOpView caixaProdutoOpViewSelected;

    //formaPagamentoSpinner
    private Spinner spinnerFormaPagamento;
    private FormaPagamento formaPagamentoSelected;

    private VendaOpView vendaOpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        vendaOpView = new VendaOpView();

        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_nova_venda);
        //banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        produtoDAO = dataBase.getProdutoDAO();
        caixaDAO = dataBase.getCaixaDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        formaPagamentoDAO = dataBase.getFormaPagamentoDAO();
        //spinner
        popularSpinner();

        inicializaCampos();
        valorPago = findViewById(R.id.valorPago);

        // variaveis e objetos dos produtos da venda
        this.listViewDeVendaProdutos = findViewById(R.id.listVendaProduto);
        this.vendaProdutoOpViewList = new ArrayList<>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.vendaProdutoOpViewList, this);
        this.listViewDeVendaProdutos.setAdapter(this.produtoVendaAdapterActivity);
        this.listViewDeVendaProdutos.setLayoutManager(new LinearLayoutManager(this));


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
        List<FormaPagamento> formaPagamentoList = new ArrayList<>();
        try {
            formaPagamentoList = new ListarFormaPagamentoTask(formaPagamentoDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spinnerFormaPagamento = findViewById(R.id.listaFormaPagamento);
        ArrayAdapter<FormaPagamento> formaPagamentoAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, formaPagamentoList);
        spinnerFormaPagamento.setAdapter(formaPagamentoAdapter);

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
    }

    private void popularSpinner() {
        try {
            caixa=  new BuscarCaixaAbertoTask(caixaDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<CaixaProdutoOpView> caixaProdutoOpViewList = new ArrayList<>();
        List<CaixaProduto> caixaProdutoList = new ArrayList<>();

        try {
            caixaProdutoList = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO, caixa.getId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        caixaProdutoList.forEach(caixaProduto -> this.populaCaixaProdutoViewList(caixaProduto, caixaProdutoOpViewList));

        spinnerCaixaproduto = findViewById(R.id.spinnerProdutoVenda);
        ArrayAdapter<CaixaProdutoOpView> spnProdutoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, caixaProdutoOpViewList);
        spinnerCaixaproduto.setAdapter(spnProdutoAdapter);
    }

    private void populaCaixaProdutoViewList(CaixaProduto caixaProduto, List<CaixaProdutoOpView> caixaProdutoOpViewList) {
        CaixaProdutoOpView caixaProdutoOpView = new CaixaProdutoOpView();
        caixaProdutoOpView.setCaixaProduto(caixaProduto);

        try {
            produto = new ConsultarProdutoTask(produtoDAO,caixaProduto.getProdutoId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProdutoOpView produtoOpView = new ProdutoOpView();
        produtoOpView.setProduto(produto);
        caixaProdutoOpView.setProdutoView(produtoOpView);

        caixaProdutoOpViewList.add(caixaProdutoOpView);
    }

    public void eventAddProduto(View view) {
        VendaProduto vendaProduto = new VendaProduto();
        vendaProdutoOpView = new VendaProdutoOpView();
        quantidadeProdutos = findViewById(R.id.Quantidade);

        caixaProdutoOpViewSelected = (CaixaProdutoOpView) spinnerCaixaproduto.getSelectedItem();

        if (caixaProdutoOpViewSelected != null) {
            if (!vendaProdutoOpViewList.stream().map(VendaProdutoOpView::getCaixaProdutoView).collect(Collectors.toList()).contains(caixaProdutoOpViewSelected)) {

                Integer quantidadeVendaProduto;
                if (this.quantidadeProdutos.getText().toString().equals("")) {
                    quantidadeVendaProduto = 1;
                } else {
                    quantidadeVendaProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
                }

                vendaProdutoOpView.setCaixaProdutoView(caixaProdutoOpViewSelected);
                vendaProduto.setQtd(quantidadeVendaProduto);
                vendaProduto.setPrecoVenda(caixaProdutoOpViewSelected.getProdutoView().getProduto().getPreco() * quantidadeVendaProduto);
                vendaProdutoOpView.setVendaProduto(vendaProduto);

                if (vendaProdutoOpView.getCaixaProdutoView().getCaixaProduto().getQtd() >= vendaProdutoOpView.getVendaProduto().getQtd()){
                    produtoVendaAdapterActivity.addProdutoVenda(vendaProdutoOpView);

                }else {
                    Toast.makeText(this, "Quantidade de Produtos da Venda passou a de Produtos do Caixa!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Produto já Selecionado na Venda!", Toast.LENGTH_LONG).show();
            }
        }
        quantidadeProdutos.setText("");
        preencherValorTotal();
        Log.d("ListaProdutodaVenda", String.valueOf(vendaProdutoOpViewList));
    }

    public void preencherValorTotal() {
        valorTotal = findViewById(R.id.valorTotal);
        Integer somaValorTotal = 0;
        for (VendaProdutoOpView vendaPView : vendaProdutoOpViewList) {
            somaValorTotal += vendaPView.getVendaProduto().getPrecoVenda();
        }
        valorTotal.setText(""+ MoneyConverter.toString(somaValorTotal));
    }

    private void finalizarVenda() {
        venda = new Venda();
        if (valorPago.getText().toString().equals("")) {
            venda.setValorPago(MoneyConverter.converteParaCentavos(valorTotal.getText().toString()));
        } else {
            venda.setValorPago(MoneyConverter.converteParaCentavos(valorPago.getText().toString()));
        }
        venda.setValorVenda(MoneyConverter.converteParaCentavos(valorTotal.getText().toString()));
        venda.setFormaPagamentoId(formaPagamentoSelected.getId());
        venda.setCaixaId(caixa.getId());

        vendaOpView.setFormaPagamento(formaPagamentoSelected);
        vendaOpView.setVenda(venda);
        vendaOpView.setVendaProdutoViewList(vendaProdutoOpViewList);


        final Object vendaViewSent = vendaOpView;
        final Bundle bundle = new Bundle();
        bundle.putBinder("vendaViewValue", new ObjectWrapperForBinder(vendaViewSent));
        startActivity(new Intent(this, ResumoVendaAcitivity.class).putExtras(bundle));
    }


}