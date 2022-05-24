package com.tcc.zipzop.adapter;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapterActivity extends RecyclerView.Adapter<ItemAdapterActivity.MyViewHolder> {

    private List<Item> itens = new ArrayList<>();
    private Context context;
    private ItemDAO dao;
    private  int longClickPosition;
    private Long id;
    public Long getId(){
        return id;
    }
    private void setId(Long id){
        this.id = id;
    }

    public ItemAdapterActivity(List<Item> itens, Context context) {
        this.itens = itens;
        this.context = context;
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(context);
        this.dao = dataBase.getItemDAO();

    }

    @NonNull
    @Override
    //config do que vai ser mostrado
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_adapter,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(String.valueOf(itens.get(position).getNome()));
        holder.qtd.setText(String.valueOf(itens.get(position).getQtd()));
        holder.valor.setText("R$:"+String.valueOf(itens.get(position).getPreco()));
        int longClickPosition = position;
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setId(itens.get(longClickPosition).getId());
                setPosicao(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView nome;
        TextView qtd;
        TextView valor;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeItem);
            qtd = itemView.findViewById(R.id.qtdItem);
            valor = itemView.findViewById(R.id.valorItem);
            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.excluir,Menu.NONE,"Excluir");
            menu.add(Menu.NONE,R.id.editar,Menu.NONE,"Editar");

        }
    }
    public void atualiza(List<Item> item) {
        this.itens.clear();
        this.itens.addAll(item);
        notifyDataSetChanged();
    }

    public void excluir(Item item){
        itens.remove(item);
        notifyDataSetChanged();
    }



    public Context getContext() {
        return this.context;
    }

    public Item getItem(int posicao) {
        return itens.get(posicao);
    }

    private int posicao;
    public int getPosicao(){
        return posicao;
    }
    public void setPosicao(int posicao){
        this.posicao = posicao;
    }

}
