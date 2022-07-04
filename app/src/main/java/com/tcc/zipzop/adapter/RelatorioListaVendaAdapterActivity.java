package com.tcc.zipzop.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.view.analytics.VendaView;

import java.util.List;

public class RelatorioListaVendaAdapterActivity extends RecyclerView.Adapter<RelatorioListaVendaAdapterActivity.MyViewHolder>{
    private Context context;
    private List<VendaView> vendaViewList;

    public RelatorioListaVendaAdapterActivity(Context context, List<VendaView> vendaViewList) {
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
