package com.tcc.zipzop.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.activity.EditarItemActivity;
import com.tcc.zipzop.activity.ItemActivity;
import com.tcc.zipzop.asynctask.ExcluirItemTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.List;


public class ItemAdapterActivity extends RecyclerView.Adapter<ItemAdapterActivity.MyViewHolder> {

    private List<Item> itens;
    private Context context;
    private ItemDAO dao;

    private Long id;
    private Long getId(){
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
                //setPosicao(holder.getAdapterPosition());
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

    public void excluir(Item item){
        this.itens.remove(item);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return this.context;
    }

    public Item getItem(int posicao) {
        return itens.get(posicao);
    }


    public MenuItem onContextItemSelected(@NonNull MenuItem menuItem) {

        Long id = (getId());
        Item item = this.dao.consultar(id);
        ItemAdapterActivity adapter = this;

        switch (menuItem.getItemId()){
            case R.id.excluir:
                new ExcluirItemTask(dao, adapter, item).execute();
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
                Item itemEscolhido = this.getItem(menuInfo.position);
                excluir(itemEscolhido);
                break;
            case R.id.editar:
                Intent intent2 = new Intent(context, EditarItemActivity.class);
                intent2.putExtra("id",id);
                context.startActivity(intent2);
                break;
        }
        return menuItem;
    }

}
