package com.tcc.zipzop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tcc.zipzop.R;
import com.tcc.zipzop.entity.NaoEntityNomeProvisorioProdutoDoCaixa;

import java.util.List;

public class ProdutoVendaAdapterActivity extends BaseAdapter {

    private Context context;
    private List<NaoEntityNomeProvisorioProdutoDoCaixa> produtosDaVenda;

    public ProdutoVendaAdapterActivity(Context context, List<NaoEntityNomeProvisorioProdutoDoCaixa>produtosDaVenda) {
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
        TextView nomeProduto = (TextView) v.findViewById(R.id.nomeProduto);
        TextView qtdProduto = (TextView) v.findViewById(R.id.qtdProduto);

        nomeProduto.setText(this.produtosDaVenda.get(posicao).getNome());
        qtdProduto.setText(String.valueOf(this.produtosDaVenda.get(posicao).getQtdSelecionada()));

        return v;
    }

    public void addProdutoVenda(NaoEntityNomeProvisorioProdutoDoCaixa pProdutoDaVenda){
        this.produtosDaVenda.add(pProdutoDaVenda);
        this.notifyDataSetChanged();
    }

    public void atualizar(List<NaoEntityNomeProvisorioProdutoDoCaixa> pProdutosDaVenda){
        this.produtosDaVenda.clear();
        this.produtosDaVenda = pProdutosDaVenda;
        this.notifyDataSetChanged();
    }


}
