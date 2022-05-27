package com.tcc.zipzop.adapter;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;
import com.tcc.zipzop.entity.ItemDoCaixa;

import java.util.ArrayList;
import java.util.List;

public class ItemCaixaAdapterActivity extends BaseAdapter {

    private Context context;
    private List<ItemDoCaixa> itensDoCaixa;

    public ItemCaixaAdapterActivity(Context context, List<ItemDoCaixa>itensDoCaixa) {
        this.context = context;
        this.itensDoCaixa = itensDoCaixa;
    }

    @Override
    public int getCount() {return this.itensDoCaixa.size();}

    @Override
    public Object getItem(int posicao) {return this.itensDoCaixa.get(posicao); }

    @Override
    public long getItemId(int posicao) {return posicao; }

    public void removerItem(int posicao) {
        this.itensDoCaixa.remove(posicao);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View v = View.inflate(this.context, R.layout.layout_item_caixa, null);
        TextView nomeItem = (TextView) v.findViewById(R.id.nomeItem);
        TextView qtdItem = (TextView) v.findViewById(R.id.qtdItem);

        nomeItem.setText(this.itensDoCaixa.get(posicao).getNome());
        qtdItem.setText(String.valueOf(this.itensDoCaixa.get(posicao).getQtdSelecionada()));

        return v;
    }

    public void addItemCaixa(ItemDoCaixa pItemDoCaixa){
        this.itensDoCaixa.add(pItemDoCaixa);
        this.notifyDataSetChanged();
    }

    public void atualizar(List<ItemDoCaixa> pItensDoCaixa){
        this.itensDoCaixa.clear();
        this.itensDoCaixa = pItensDoCaixa;
        this.notifyDataSetChanged();
    }


}
