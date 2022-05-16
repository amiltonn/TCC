package com.tcc.zipzop;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.entity.Item;

import java.util.ArrayList;


public class ItemAdapterActivity extends RecyclerView.Adapter<ItemAdapterActivity.MyViewHolder> {

   private ArrayList nomeItem;
   private Context context;

    public ItemAdapterActivity(ArrayList nomeItem, Context context) {
        this.nomeItem = nomeItem;
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
        holder.nome.setText(String.valueOf(nomeItem.get(position)));


    }

    @Override
    public int getItemCount() {
        return nomeItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton nome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.Bt_item);


        }
    }
}
