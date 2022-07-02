package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.CaixaProdutoView;
import com.tcc.zipzop.view.ProdutoView;
import com.tcc.zipzop.view.VendaProdutoView;
import com.tcc.zipzop.view.VendaView;

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
    private ListView listViewDeVendaProdutos;
    private List<VendaProdutoView> vendaProdutoViewList;
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
    private VendaProdutoView vendaProdutoView;

    //caixaProdutoSpinner
    private Spinner spinnerCaixaproduto;
    CaixaProdutoView caixaProdutoViewSelected;

    //formaPagamentoSpinner
    private Spinner spinnerFormaPagamento;
    private FormaPagamento formaPagamentoSelected;

    private VendaView vendaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        vendaView = new VendaView();

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
        this.listViewDeVendaProdutos = (ListView) this.findViewById(R.id.listVendaProduto);
        this.vendaProdutoViewList = new ArrayList<>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.vendaProdutoViewList);
        this.listViewDeVendaProdutos.setAdapter(this.produtoVendaAdapterActivity);


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

        List<CaixaProdutoView> caixaProdutoViewList = new ArrayList<>();
        List<CaixaProduto> caixaProdutoList = new ArrayList<>();

        try {
            caixaProdutoList = new ListaCaixaProdutoAbertoTask(caixaProdutoDAO, caixa.getId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        caixaProdutoList.forEach(caixaProduto -> this.populaCaixaProdutoViewList(caixaProduto, caixaProdutoViewList));

        spinnerCaixaproduto = findViewById(R.id.spinnerProdutoVenda);
        ArrayAdapter<CaixaProdutoView> spnProdutoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, caixaProdutoViewList);
        spinnerCaixaproduto.setAdapter(spnProdutoAdapter);
    }

    private void populaCaixaProdutoViewList(CaixaProduto caixaProduto, List<CaixaProdutoView> caixaProdutoViewList) {
        CaixaProdutoView caixaProdutoView = new CaixaProdutoView();
        caixaProdutoView.setCaixaProduto(caixaProduto);

        try {
            produto = new ConsultarProdutoTask(produtoDAO,caixaProduto.getProdutoId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProdutoView produtoView = new ProdutoView();
        produtoView.setProduto(produto);
        caixaProdutoView.setProdutoView(produtoView);

        caixaProdutoViewList.add(caixaProdutoView);
    }

    public void eventAddProduto(View view) {
        VendaProduto vendaProduto = new VendaProduto();
        vendaProdutoView = new VendaProdutoView();
        quantidadeProdutos = findViewById(R.id.Quantidade);

        caixaProdutoViewSelected = (CaixaProdutoView) spinnerCaixaproduto.getSelectedItem();

        if (caixaProdutoViewSelected != null && !vendaProdutoViewList.stream().map(VendaProdutoView::getCaixaProdutoView).collect(Collectors.toList()).contains(caixaProdutoViewSelected)){

            Integer quantidadeVendaProduto;
            if(this.quantidadeProdutos.getText().toString().equals("")) {
                quantidadeVendaProduto = 1;
            } else {
                quantidadeVendaProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }

            vendaProdutoView.setCaixaProdutoView(caixaProdutoViewSelected);
            vendaProduto.setQtd(quantidadeVendaProduto);
            vendaProduto.setPrecoVenda(caixaProdutoViewSelected.getProdutoView().getProduto().getPreco() * quantidadeVendaProduto);
            vendaProdutoView.setVendaProduto(vendaProduto);

            produtoVendaAdapterActivity.addProdutoVenda(vendaProdutoView);
        }
        preencherValorTotal();
        Log.d("ListaProdutodaVenda", String.valueOf(vendaProdutoViewList));
    }

    private void preencherValorTotal() {
        valorTotal = findViewById(R.id.valorTotal);
        Integer somaValorTotal = 0;
        for (VendaProdutoView vendaPView : vendaProdutoViewList) {
            somaValorTotal += vendaPView.getVendaProduto().getPrecoVenda();
        }
        valorTotal.setText(""+somaValorTotal);
    }
    private void finalizarVenda() {
        venda = new Venda();
        if (valorPago.getText().toString().equals("")) {
            venda.setValorPago(Integer.parseInt(valorTotal.getText().toString()));
        } else {
            venda.setValorPago(Integer.parseInt(valorPago.getText().toString()));
        }
        venda.setValorVenda(Integer.parseInt(valorTotal.getText().toString()));
        venda.setFormaPagamentoId(formaPagamentoSelected.getId());
        venda.setCaixaId(caixa.getId());

        vendaView.setFormaPagamento(formaPagamentoSelected);
        vendaView.setVenda(venda);
        vendaView.setVendaProdutoViewList(vendaProdutoViewList);

        final Object vendaViewSent = vendaView;
        final Bundle bundle = new Bundle();
        bundle.putBinder("vendaViewValue", new ObjectWrapperForBinder(vendaViewSent));
        startActivity(new Intent(this, ResumoVendaAcitivity.class).putExtras(bundle));
    }


}