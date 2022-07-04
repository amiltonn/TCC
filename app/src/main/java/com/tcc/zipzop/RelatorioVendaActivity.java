package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tcc.zipzop.typeconverter.DateTimeConverter;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.typeconverter.ObjectWrapperForBinder;
import com.tcc.zipzop.view.analytics.ProdutoView;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RelatorioVendaActivity extends AppCompatActivity {

  private VendaView vendaView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.Actionbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_relatorio_venda);

    final Object vendaViewReceived = ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("vendaViewValue")).getData();
    this.vendaView = (VendaView) vendaViewReceived;

    filtrarVendaView();

    resumoVendaView();
  }


  private void filtrarVendaView(){
    List<ProdutoView> produtoViewList = vendaView.getCaixaView().getEstoqueView().getProdutoViewList().stream().filter(produtoView -> !(produtoView.getCaixaProdutoView() == null || produtoView.getCaixaProdutoView().getVendaProdutoView() == null)).collect(Collectors.toList());
    vendaView.getCaixaView().getEstoqueView().setProdutoViewList(produtoViewList);
  }

  private void resumoVendaView() {
    TableLayout tabelaVendaView = findViewById(R.id.tabela);
    Integer total = 0;
    Integer descontos = 0;
    Integer totalCusto = 0;
    Integer pago = 0;
    Integer totalDesconto = 0;
    Integer lucro = 0;
    for (ProdutoView produtoView : vendaView.getCaixaView().getEstoqueView().getProdutoViewList()) {
      Integer valorOriginal = produtoView.getProduto().getPreco() * produtoView.getCaixaProdutoView().getVendaProdutoView().getVendaProduto().getQtd();
      Integer desconto = valorOriginal - produtoView.getCaixaProdutoView().getVendaProdutoView().getVendaProduto().getPrecoVenda();
      Integer custo = produtoView.getProduto().getCusto();
      total += valorOriginal;
      descontos += desconto;
      totalCusto += custo;
      String[] visualizacaoVendaView = {
          produtoView.getProduto().getNome(),
          String.valueOf(produtoView.getCaixaProdutoView().getVendaProdutoView().getVendaProduto().getQtd()),
          MoneyConverter.toString(produtoView.getProduto().getPreco()),
          MoneyConverter.toString(custo),
          MoneyConverter.toString(desconto)
      };
      pago = vendaView.getVenda().getValorVenda();
      totalDesconto = total - pago;
      lucro = pago - totalCusto;
      TableRow row = new TableRow(getBaseContext());
      TextView textViewFirst = null;
      TextView textView = null;
      for(int i=0; i < 5; i++) {
        textView = new TextView(getBaseContext());
        switch (i) {
          case 0:
            textViewFirst = findViewById(R.id.nome);
            break;
          case 1:
            textViewFirst = findViewById(R.id.qtd);
            break;
          case 2:
            textViewFirst = findViewById(R.id.preco);
            break;
          case 3:
            textViewFirst = findViewById(R.id.custo);
            break;
          case 4:
            textViewFirst = findViewById(R.id.desconto);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + i);
        }
        textView.setPadding(textViewFirst.getPaddingLeft(), textViewFirst.getCompoundPaddingTop(), textViewFirst.getCompoundPaddingRight(), textViewFirst.getCompoundPaddingBottom());
        textView.setLayoutParams(textViewFirst.getLayoutParams());
        textView.setBackgroundResource(R.color.cordefundo);
        textView.setText(visualizacaoVendaView[i]);
        row.addView(textView);
        row.setGravity(Gravity.CENTER);
      }
      tabelaVendaView.addView(row);
    }
    TextView campoData, campoTotal, campoPago, campoTotalCusto, campoDescontos, campoTotalDesconto, campoLucro;
    campoData = findViewById(R.id.dataVenda);
    campoData.setText(DateTimeConverter.dataFormatada(vendaView.getVenda().getDataPagamento()));
    campoTotal = findViewById(R.id.total);
    campoTotal.setText("R$"+MoneyConverter.toString(total));
    campoPago = findViewById(R.id.pago);
    campoPago.setText("R$"+MoneyConverter.toString(pago));
    campoTotalCusto = findViewById(R.id.totalCusto);
    campoTotalCusto.setText("R$"+MoneyConverter.toString(totalCusto));
    campoDescontos = findViewById(R.id.descontos);
    campoDescontos.setText("R$"+MoneyConverter.toString(descontos));
    campoTotalDesconto = findViewById(R.id.totalDesconto);
    campoTotalDesconto.setText("R$"+MoneyConverter.toString(totalDesconto));
    campoLucro = findViewById(R.id.lucro);
    campoLucro.setText("R$"+MoneyConverter.toString(lucro));

  }
}
