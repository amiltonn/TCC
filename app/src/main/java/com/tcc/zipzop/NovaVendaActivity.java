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
import com.tcc.zipzop.asynctask.venda.ConsultarVendaAbertaTask;
import com.tcc.zipzop.asynctask.venda.FecharVendaTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaProdutoTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.asynctask.venda.SalvarVendaProdutoTask;
import com.tcc.zipzop.asynctask.venda.SalvarVendaTask;
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
    private ListView listarVendaProdutos;
    private List<VendaProdutoView> vendaProdutoViewList;
    private List<CaixaProdutoView> caixaProdutoViewList;
    private ProdutoVendaAdapterActivity produtoVendaAdapterActivity;
    //Spinner
    private Spinner spinnerCaixaproduto;
    List<CaixaProduto> listaCaixaProdutos;
    //banco
    private VendaDAO vendaDAO;
    private VendaProdutoDAO vendaProdutoDAO;
    private FormaPagamentoDAO formaPagamentoDAO;
    private CaixaDAO caixaDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private ProdutoDAO produtoDAO;
    //entity
    private Venda venda;
    private Caixa caixa;
    private CaixaProdutoView caixaProdutoView;
    private Produto produto;
    private VendaProdutoView vendaProdutoView;
    //formaPagamento
    private Spinner spinnerFormaPagamento;
    ArrayAdapter<FormaPagamento> formaPagamentoAdapter;
    private FormaPagamento formaPagamentoSelected;
    List<FormaPagamento> formaPagamentos;
    //List
    private List<VendaProduto> vendaProdutoList;

    private VendaView vendaView;

    public VendaView getVendaView(){
        return vendaView;
    }
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

        // variaveis e objetos dos produtos da venda
        this.listarVendaProdutos = (ListView) this.findViewById(R.id.listVendaProduto);
        this.vendaProdutoViewList = new ArrayList<VendaProdutoView>();
        this.produtoVendaAdapterActivity = new ProdutoVendaAdapterActivity(NovaVendaActivity.this, this.vendaProdutoViewList);
        this.listarVendaProdutos.setAdapter(this.produtoVendaAdapterActivity);


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
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        caixaProdutoViewList = new ArrayList<CaixaProdutoView>();
        listaCaixaProdutos.forEach(caixaProduto ->{
            caixaProdutoView = new CaixaProdutoView();
            caixaProdutoView.setCaixaProduto(caixaProduto);

            try {
                produto = new ConsultarProdutoTask(produtoDAO,caixaProduto.getProdutoId()).execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ProdutoView pv = new ProdutoView();
            pv.setProduto(produto);
            caixaProdutoView.setProdutoView(pv);

            caixaProdutoViewList.add(caixaProdutoView);
        });
        spinnerCaixaproduto = findViewById(R.id.spinnerProdutoVenda);
        ArrayAdapter<CaixaProdutoView> spnProdutoAdapter = new ArrayAdapter<CaixaProdutoView>(this, android.R.layout.simple_dropdown_item_1line, this.caixaProdutoViewList);
        spinnerCaixaproduto.setAdapter(spnProdutoAdapter);
    }

    public void eventAddProduto(View view) {
        VendaProduto vendaProduto = new VendaProduto();
        vendaProdutoView = new VendaProdutoView();
        quantidadeProdutos = findViewById(R.id.Quantidade);

        CaixaProdutoView caixaProdutoViewSelecionado = (CaixaProdutoView) spinnerCaixaproduto.getSelectedItem();

        if (caixaProdutoViewSelecionado != null && !vendaProdutoViewList.stream().map(VendaProdutoView::getCaixaProdutoView)
                .collect(Collectors.toList()).contains(caixaProdutoViewSelecionado)){
            int quantidadeVendaProduto;
            if(this.quantidadeProdutos.getText().toString().equals("")) {
                quantidadeVendaProduto = 1;
            }else {
                quantidadeVendaProduto = Integer.parseInt(this.quantidadeProdutos.getText().toString());
            }
            vendaProdutoView.setCaixaProdutoView(caixaProdutoViewSelecionado);
            vendaProduto.setQtd(quantidadeVendaProduto);
            vendaProduto.setPrecoVenda(caixaProdutoViewSelecionado.getProdutoView().getProduto().getPreco() * quantidadeVendaProduto);
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
        if (valorPago.getText().toString().equals("")){
            venda.setValorPago(Integer.parseInt(valorTotal.getText().toString()));
        }else{
            venda.setValorPago(Integer.parseInt(valorPago.getText().toString()));
        }
        venda.setValorVenda(Integer.parseInt(valorTotal.getText().toString()));
        //venda.setVendaLocalId(1);
        venda.setFormaPagamentoId(formaPagamentoSelected.getId());
        venda.setCaixaId(caixa.getId());

        vendaView.setFormaPagamento(formaPagamentoSelected);
        vendaView.setVenda(venda);
        vendaView.setVendaProdutoList(vendaProdutoViewList.stream().map(VendaProdutoView::getVendaProduto).collect(Collectors.toList()));

        final Object vendaProdutoViewSent = vendaProdutoView;
        final Bundle bundle = new Bundle();
        bundle.putBinder("vendaProdutoViewValue", new ObjectWrapperForBinder(vendaProdutoViewSent));
        startActivity(new Intent(this, ResumoVendaAcitivity.class).putExtras(bundle));

        // a partir daqui..

        new SalvarVendaTask(vendaDAO, vendaView.getVenda()).execute();
        salvarVendaProduto();
    }

    private void salvarVendaProduto() {
        try {
            vendaView.setVenda(new ConsultarVendaAbertaTask(vendaDAO).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        vendaProdutoViewList.forEach(vendaP ->{
            VendaProduto vendaProduto = vendaP.getVendaProduto();
            vendaProduto.setCaixaProdutoId(vendaP.getCaixaProdutoView().getCaixaProduto().getId());
            vendaProduto.setVendaId(venda.getId());
            Log.d("VendaProduto", String.valueOf(vendaProduto));

            new SalvarVendaProdutoTask(vendaProdutoDAO, this, vendaProduto).execute();
        });
        new FecharVendaTask(vendaDAO).execute();

    }


}