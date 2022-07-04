package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.tcc.zipzop.adapter.RelatorioListaVendaAdapterActivity;
import com.tcc.zipzop.asynctask.relatorio.vendaView.ConsultarVendaViewTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RelatorioListaVendaActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private RelatorioListaVendaAdapterActivity relatorioListaVendaAdapterActivity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.Actionbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_relatorio_lista_venda);
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
    List<VendaView> vendaViewList = new ArrayList<>();
    try {
      List<Venda> vendaList = new ListarVendaTask(dataBase.getVendaDAO()).execute().get();
      for (Venda venda : vendaList) {
        try {
          vendaViewList.add(new ConsultarVendaViewTask(this, venda.getId()).execute().get());
        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      List treco = vendaViewList;
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    recyclerView = findViewById(R.id.listaVendaViewRecycleView);
    relatorioListaVendaAdapterActivity = new RelatorioListaVendaAdapterActivity(this, vendaViewList);
    recyclerView.setAdapter(relatorioListaVendaAdapterActivity);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  public void onSelectedVendaView(VendaView vendaView) {
    Intent intent = new Intent(this, RelatorioVendaActivity.class);
    startActivity(intent);
  }

}