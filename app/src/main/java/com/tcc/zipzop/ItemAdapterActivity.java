package com.tcc.zipzop;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapterActivity extends RecyclerView.Adapter<ItemAdapterActivity.MyViewHolder> {

   private List<Item> itens;
   private Context context;

    public ItemAdapterActivity(List<Item> itens, Context context) {
        this.itens = itens;
        this.context = context;

    }


    @NonNull
    @Override
    //config do que vai ser mostrado
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_adapter,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(String.valueOf(itens.get(position).getNome()));
        Long id = itens.get(position).getId();
        holder.nome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosicao(holder.getAdapterPosition());
                setId(id);

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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeItem);

            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.excluir,Menu.NONE,"Excluir");
            menu.add(Menu.NONE,R.id.editar,Menu.NONE,"Editar");


        }
    }
    private int posicao;
    public int getPosicao(){
        return posicao;
    }
    public void setPosicao(int posicao){
        this.posicao = posicao;
    }
    private Long id;
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}
