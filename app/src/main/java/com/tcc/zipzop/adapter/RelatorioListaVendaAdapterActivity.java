package com.tcc.zipzop.adapter;

import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.RelatorioListaVendaActivity;
import com.tcc.zipzop.typeconverter.DateTimeConverter;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.List;

public class RelatorioListaVendaAdapterActivity extends RecyclerView.Adapter<RelatorioListaVendaAdapterActivity.MyViewHolder>{
    private RelatorioListaVendaActivity context;
    private List<VendaView> vendaViewList;

    public RelatorioListaVendaAdapterActivity(RelatorioListaVendaActivity context, List<VendaView> vendaViewList) {
        this.context = context;
        this.vendaViewList = vendaViewList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vendaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_relatorio_lista_venda_adapter, parent, false);
        return new MyViewHolder(vendaView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.data.setText(DateTimeConverter.dataFormatada(vendaViewList.get(position).getVenda().getDataPagamento()));
        holder.pago.setText("R$"+ MoneyConverter.toString(vendaViewList.get(position).getVenda().getValorPago()));
        holder.total.setText("R$"+MoneyConverter.toString(vendaViewList.get(position).getVenda().getValorVenda()));
        int clickPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                context.onSelectedVendaView(vendaViewList.get(clickPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendaViewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView data;
        TextView pago;
        TextView total;
        public MyViewHolder(@NonNull View vendaViewView) {
            super(vendaViewView);
            data = vendaViewView.findViewById(R.id.dataVenda);
            pago = vendaViewView.findViewById(R.id.pago);
            total = vendaViewView.findViewById(R.id.total);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
