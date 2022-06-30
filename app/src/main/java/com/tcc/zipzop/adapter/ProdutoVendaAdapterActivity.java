package com.tcc.zipzop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.view.VendaProdutoView;

import java.util.List;

public class ProdutoVendaAdapterActivity extends BaseAdapter {

    private Context context;
    private List<VendaProdutoView> produtosDaVenda;

    public ProdutoVendaAdapterActivity(Context context, List<VendaProdutoView>produtosDaVenda) {
        this.context = context;
        this.produtosDaVenda = produtosDaVenda;
    }

    @Override
    public int getCount() {return this.produtosDaVenda.size();}

    @Override
    public Object getItem(int posicao) {return this.produtosDaVenda.get(posicao); }

    @Override
    public long getItemId(int posicao) {return posicao; }

    public void removerProduto(int posicao) {
        this.produtosDaVenda.remove(posicao);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View v = View.inflate(this.context, R.layout.layout_produto_caixa, null);
        TextView nomeProduto = (TextView) v.findViewById(R.id.nomeProdutoCaixaAberto);
        TextView qtdProduto = (TextView) v.findViewById(R.id.qtdProdutoCaixaAberto);

        nomeProduto.setText(this.produtosDaVenda.get(posicao).getProdutoNome());
        qtdProduto.setText(String.valueOf(this.produtosDaVenda.get(posicao).getQtd()));

        return v;
    }

    public void addProdutoVenda(VendaProdutoView pProdutoDaVenda){
        this.produtosDaVenda.add(pProdutoDaVenda);
        this.notifyDataSetChanged();
    }

    public void atualizar(List<VendaProdutoView> pProdutosDaVenda){
        this.produtosDaVenda.clear();
        this.produtosDaVenda = pProdutosDaVenda;
        this.notifyDataSetChanged();
    }


}
