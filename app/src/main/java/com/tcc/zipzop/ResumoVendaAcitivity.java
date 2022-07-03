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

import com.tcc.zipzop.asynctask.venda.ConsultarVendaAbertaTask;
import com.tcc.zipzop.asynctask.venda.FecharVendaTask;
import com.tcc.zipzop.asynctask.venda.SalvarVendaTask;
import com.tcc.zipzop.asynctask.venda.vendaProduto.SalvarVendaProdutoActivityTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.VendaProduto;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.VendaProdutoView;
import com.tcc.zipzop.view.VendaView;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiresApi(api = Build.VERSION_CODES.N)

public class ResumoVendaAcitivity extends AppCompatActivity {
   private AppCompatButton btSalvarVenda;
   private TextView campoValorTotal,campoFormaPagamento;

    private VendaView vendaView;
    private List<VendaProdutoView> vendaProdutoViewList;

    //banco
    private VendaDAO vendaDAO;
    private VendaProdutoDAO vendaProdutoDAO;
    private CaixaProdutoDAO caixaProdutoDAO;

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
        final Object vendaViewReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("vendaViewValue")).getData();
        vendaView= (VendaView) vendaViewReceived;
        Log.d("Resumo", String.valueOf(vendaView));
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
        vendaProdutoViewList = vendaView.getVendaProdutoViewList();
        TableLayout tableLayoutVendaProduto = findViewById(R.id.tableLayoutVendaProduto);
        vendaProdutoViewList.forEach(vPVenda ->{
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
        campoValorTotal.setText(""+MoneyConverter.toString(vendaView.getVenda().getValorPago()));
        campoFormaPagamento = findViewById(R.id.formaPagamentoResumo);
        campoFormaPagamento.setText(""+vendaView.getFormaPagamento().getNome());
    }




    private void salvarVenda() {
        new SalvarVendaTask(vendaDAO, vendaView.getVenda()).execute();
        try {
            vendaView.setVenda(new ConsultarVendaAbertaTask(vendaDAO).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        vendaProdutoViewList.forEach(vendaProdutoView ->{
            VendaProduto vendaProduto = vendaProdutoView.getVendaProduto();
            vendaProduto.setCaixaProdutoId(vendaProdutoView.getCaixaProdutoView().getCaixaProduto().getId());
            vendaProduto.setVendaId(vendaView.getVenda().getId());
            Log.d("VendaProduto", String.valueOf(vendaProduto));

            new SalvarVendaProdutoActivityTask(vendaProdutoDAO, vendaProduto, caixaProdutoDAO).execute();
        });
        new FecharVendaTask(vendaDAO).execute();


        startActivity(new Intent(this, VendaActivity.class));
    }
}