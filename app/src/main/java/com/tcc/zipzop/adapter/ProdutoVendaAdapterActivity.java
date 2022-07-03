package com.tcc.zipzop.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.NovaVendaActivity;
import com.tcc.zipzop.R;
import com.tcc.zipzop.typeconverter.MoneyConverter;
import com.tcc.zipzop.view.operations.VendaProdutoOpView;

import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.N)
public class ProdutoVendaAdapterActivity extends RecyclerView.Adapter<ProdutoVendaAdapterActivity.MyViewHolder>{

    private Context context;
    private List<VendaProdutoOpView> produtosDaVenda;
    private NovaVendaActivity  novaVendaActivity;

    public ProdutoVendaAdapterActivity(Context context, List<VendaProdutoOpView> produtosDaVenda, NovaVendaActivity novaVendaActivity) {
        this.context = context;
        this.produtosDaVenda = produtosDaVenda;
        this.novaVendaActivity = novaVendaActivity;
    }


    @NonNull
    @Override
    public ProdutoVendaAdapterActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View produtoVendaView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_produto_venda,parent,false);

        return new MyViewHolder(produtoVendaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoVendaAdapterActivity.MyViewHolder holder, int position) {
        holder.nomeProduto.setText(this.produtosDaVenda.get(position).getCaixaProdutoView().getProdutoView()
                .getProduto().getNome());
        holder.qtdVendaProduto.setText(String.valueOf(this.produtosDaVenda.get(position).getVendaProduto().getQtd())+" ");
        holder.campoValor.setText(MoneyConverter.toString(produtosDaVenda.get(position).getVendaProduto().getPrecoVenda()));

        holder.campoValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                produtosDaVenda.get(holder.getAdapterPosition()).getVendaProduto().setPrecoVenda(
                        MoneyConverter.converteParaCentavos(holder.campoValor.getText().toString())
                );
                if (produtosDaVenda.get(holder.getAdapterPosition()).getVendaProduto().getPrecoVenda() == null){
                    produtosDaVenda.get(holder.getAdapterPosition()).getVendaProduto().setPrecoVenda(0);
                    novaVendaActivity.preencherValorTotal();
                }else {
                    novaVendaActivity.preencherValorTotal();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return produtosDaVenda.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView nomeProduto, qtdVendaProduto;
        EditText campoValor;
        public MyViewHolder(@NonNull View produtoVendaView) {
            super(produtoVendaView);
            nomeProduto = (TextView) produtoVendaView.findViewById(R.id.nomeProdutoVendaList);
            qtdVendaProduto = (TextView) produtoVendaView.findViewById(R.id.qtdProdutoVendaList);
            campoValor = (EditText) produtoVendaView.findViewById(R.id.valorPagoEdit);
            produtoVendaView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
    public void addProdutoVenda(VendaProdutoOpView pProdutoDaVenda){
        this.produtosDaVenda.add(pProdutoDaVenda);
        this.notifyDataSetChanged();
    }
}