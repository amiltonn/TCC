package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tcc.zipzop.asynctask.caixa.caixaFundo.ConsultarCaixaFundoPeloCaixaIdTask;
import com.tcc.zipzop.asynctask.caixa.caixaFundo.SalvarCaixaFundoTask;
import com.tcc.zipzop.asynctask.venda.ConsultarVendaAbertaTask;
import com.tcc.zipzop.asynctask.venda.FecharVendaTask;
import com.tcc.zipzop.asynctask.venda.SalvarVendaTask;
import com.tcc.zipzop.asynctask.venda.vendaProduto.SalvarVendaProdutoActivityTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.CaixaFundo;
import com.tcc.zipzop.entity.VendaProduto;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.operations.VendaProdutoOpView;
import com.tcc.zipzop.view.operations.VendaOpView;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiresApi(api = Build.VERSION_CODES.N)

public class ResumoVendaAcitivity extends AppCompatActivity {
   private AppCompatButton btSalvarVenda;
   private TextView campoValorTotal,campoFormaPagamento,campoTroco,trocoTextView;

    private VendaOpView vendaOpView;
    private List<VendaProdutoOpView> vendaProdutoOpViewList;

    //banco
    private VendaDAO vendaDAO;
    private VendaProdutoDAO vendaProdutoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;
    private CaixaFundoDAO caixaFundoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_resumo_venda_acitivity);

        //banco
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        vendaDAO = dataBase.getVendaDAO();
        vendaProdutoDAO = dataBase.getVendaProdutoDAO();
        caixaProdutoDAO = dataBase.getCaixaProdutoDAO();
        caixaFundoDAO = dataBase.getCaixaFundoDAO();
        final Object vendaViewReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("vendaViewValue")).getData();
        vendaOpView = (VendaOpView) vendaViewReceived;
        Log.d("Resumo", String.valueOf(vendaOpView));
        resumoProdutoVendatable();
        preenchercampos();
        ///botão salvar venda
        btSalvarVenda = findViewById(R.id.salvarVenda);
        btSalvarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarVenda();
            }
        });

    }



    private void resumoProdutoVendatable() {
        vendaProdutoOpViewList = vendaOpView.getVendaProdutoViewList();
        TableLayout tableLayoutVendaProduto = findViewById(R.id.tableLayoutVendaProduto);
        vendaProdutoOpViewList.forEach(vPVenda ->{
            String [] visualizacaoVendaProduto = {vPVenda.getCaixaProdutoView().getProdutoView().getProduto().getNome(),
            String.valueOf(MoneyConverter.toString(vPVenda.getCaixaProdutoView().getProdutoView().getProduto().getPreco())),
                    String.valueOf(vPVenda.getVendaProduto().getQtd()),
                    String.valueOf(MoneyConverter.toString(vPVenda.getVendaProduto().getPrecoVenda()))};
            Log.d("VIZUALIZAÇAO", String.valueOf(visualizacaoVendaProduto));
            TableRow row = new TableRow(getBaseContext());
            TextView textView = null;
            for (int i=0;i<4;i++) {
                textView = new TextView(getBaseContext());
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(10, 10, 10, 10);
                textView.setBackgroundResource(R.color.botao);
                textView.setText(visualizacaoVendaProduto[i]);
                textView.setTextColor(getResources().getColor(R.color.letra));
                row.addView(textView);
            }
            tableLayoutVendaProduto.addView(row);
        });

    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(menuItem);
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    private void preenchercampos() {
        campoValorTotal = findViewById(R.id.valorPagoResumo);
        campoValorTotal.setText(""+MoneyConverter.toString(vendaOpView.getVenda().getValorPago()));
        campoFormaPagamento = findViewById(R.id.formaPagamentoResumo);
        campoFormaPagamento.setText(""+ vendaOpView.getFormaPagamento().getNome());
        trocoTextView = findViewById(R.id.trocoTextView);
        campoTroco = findViewById(R.id.campoTroco);
        if (vendaOpView.getFormaPagamento().getNome().equals("DINHEIRO")){
            trocoTextView.setVisibility(View.VISIBLE);
            campoTroco.setVisibility(View.VISIBLE);
            campoTroco.setText(""+MoneyConverter.toString(vendaOpView.getVenda().getValorPago()-
                    vendaOpView.getVenda().getValorVenda()));
        }
    }




    private void salvarVenda() {
        new SalvarVendaTask(vendaDAO, vendaOpView.getVenda()).execute();
//        CaixaFundo caixaFundo = new CaixaFundo();
        try {
            vendaOpView.setVenda(new ConsultarVendaAbertaTask(vendaDAO).execute().get());
//            caixaFundo = new ConsultarCaixaFundoPeloCaixaIdTask(caixaFundoDAO, vendaOpView.getVenda().getCaixaId()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        vendaProdutoOpViewList.forEach(vendaProdutoOpView ->{
            VendaProduto vendaProduto = vendaProdutoOpView.getVendaProduto();
            vendaProduto.setCaixaProdutoId(vendaProdutoOpView.getCaixaProdutoView().getCaixaProduto().getId());
            vendaProduto.setVendaId(vendaOpView.getVenda().getId());
            Log.d("VendaProduto", String.valueOf(vendaProduto));

            new SalvarVendaProdutoActivityTask(vendaProdutoDAO, vendaProduto, caixaProdutoDAO).execute();
        });

//        caixaFundo.setDataAlteracao(null);
//        caixaFundo.setId(null);
//        caixaFundo.setValor(caixaFundo.getValor() - MoneyConverter.converteParaCentavos(campoTroco.getText().toString()));
//        new SalvarCaixaFundoTask(caixaFundoDAO, caixaFundo).execute();

        new FecharVendaTask(vendaDAO).execute();


        startActivity(new Intent(this, VendaActivity.class));
    }
}