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


public class ItemAdapterActivity extends RecyclerView.Adapter<ItemAdapterActivity.MyViewHolder> {

   private ArrayList nomeItem, idItem,qtd,precoVenda;
   private Context context;

    public ItemAdapterActivity(ArrayList nomeItem,ArrayList idItem,ArrayList qtd,ArrayList precoVenda, Context context) {
        this.nomeItem = nomeItem;
        this.context = context;
        this.idItem = idItem;
        this.qtd =  qtd;
        this.precoVenda = precoVenda;
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
        holder.nome.setText(String.valueOf(nomeItem.get(position)));

        holder.nome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosicao(holder.getAdapterPosition());
                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return nomeItem.size();
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

}
