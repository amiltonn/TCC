package com.tcc.zipzop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.view.CaixaProdutoView;

import java.util.List;

public class ProdutoCaixaAbertoAdapterActivity extends BaseAdapter {

    private Context context;
    private List<CaixaProdutoView> caixaProdutos;
    public ProdutoCaixaAbertoAdapterActivity(Context context,List<CaixaProdutoView> caixaProdutos){
        this.context = context;
        this.caixaProdutos = caixaProdutos;
    }


    @Override
    public int getCount() {
        return caixaProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return caixaProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.activity_lista_produto_adapter_caixa_aberto,null);
        TextView nomeProduto = v.findViewById(R.id.nomeProdutoCaixaAberto);
        TextView qtdProduto = v.findViewById(R.id.qtdProdutoCaixaAberto);
        // colocando os dados no campo
        nomeProduto.setText(caixaProdutos.get(position).getProdutoNome());
        qtdProduto.setText(""+caixaProdutos.get(position).getQtd());

        return v;
    }
}
