package com.tcc.zipzop.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.entity.NaoEntityNomeProvisorioProdutoDoCaixa;

import java.util.List;

public class ProdutoCaixaAdapterActivity extends BaseAdapter {

    private Context context;
    private List<NaoEntityNomeProvisorioProdutoDoCaixa> produtosDoCaixa;

    public ProdutoCaixaAdapterActivity(Context context, List<NaoEntityNomeProvisorioProdutoDoCaixa>produtosDoCaixa) {
        this.context = context;
        this.produtosDoCaixa = produtosDoCaixa;
    }

    @Override
    public int getCount() {return this.produtosDoCaixa.size();}

    @Override
    public Object getItem(int posicao) {return this.produtosDoCaixa.get(posicao); }

    @Override
    public long getItemId(int posicao) {return posicao; }

    public void removerProduto(int posicao) {
        this.produtosDoCaixa.remove(posicao);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View v = View.inflate(this.context, R.layout.layout_produto_caixa, null);
        TextView nomeProduto = (TextView) v.findViewById(R.id.nomeProduto);
        TextView qtdProduto = (TextView) v.findViewById(R.id.qtdProduto);

        nomeProduto.setText(this.produtosDoCaixa.get(posicao).getNome());
        qtdProduto.setText(String.valueOf(this.produtosDoCaixa.get(posicao).getQtdSelecionada()));

        return v;
    }

    public void addProdutoCaixa(NaoEntityNomeProvisorioProdutoDoCaixa pProdutoDoCaixa){
        this.produtosDoCaixa.add(pProdutoDoCaixa);
        this.notifyDataSetChanged();
    }

    public void atualizar(List<NaoEntityNomeProvisorioProdutoDoCaixa> pProdutosDoCaixa){
        this.produtosDoCaixa.clear();
        this.produtosDoCaixa = pProdutosDoCaixa;
        this.notifyDataSetChanged();
    }


}
