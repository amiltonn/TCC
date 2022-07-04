package com.tcc.zipzop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.tcc.zipzop.asynctask.relatorio.vendaView.ConsultarVendaViewTask;
import com.tcc.zipzop.asynctask.venda.ListarVendaTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.entity.Venda;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RelatorioListaVendaActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_relatorio_lista_venda);
    ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
    try {
      List<Venda> vendaList = new ListarVendaTask(dataBase.getVendaDAO()).execute().get();
      List<VendaView> vendaViewList = new ArrayList<>();
      vendaList.forEach(venda -> {
        try {
          vendaViewList.add(new ConsultarVendaViewTask(this, venda.getId()).execute().get());
        } catch (ExecutionException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
      List treco = vendaViewList;
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}