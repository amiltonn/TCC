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
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.Produto;
import com.tcc.zipzop.typeconverter.MoneyConverter;

import java.util.ArrayList;
import java.util.List;


public class ProdutoAdapterActivity extends RecyclerView.Adapter<ProdutoAdapterActivity.MyViewHolder> {

    private List<Produto> produtos = new ArrayList<>();
    private Context context;
    private ProdutoDAO dao;
    private  int longClickPosition;
    private Integer id;
    public Integer getId(){
        return id;
    }
    private void setId(Integer id){
        this.id = id;
    }

    public ProdutoAdapterActivity(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(context);
        this.dao = dataBase.getProdutoDAO();

    }

    @NonNull
    @Override
    //config do que vai ser mostrado
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View produtoLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_produto_adapter,parent,false);

        return new MyViewHolder(produtoLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nome.setText(String.valueOf(produtos.get(position).getNome()));
        holder.qtd.setText(String.valueOf(produtos.get(position).getQtd()));
        holder.valor.setText("R$:"+ MoneyConverter.toString(produtos.get(position).getPreco()));
        int longClickPosition = position;
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setId(produtos.get(longClickPosition).getId());
                setPosicao(holder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView nome;
        TextView qtd;
        TextView valor;
        public MyViewHolder(@NonNull View produtoView) {
            super(produtoView);
            nome = produtoView.findViewById(R.id.nomeProdutoCaixaAberto);
            qtd = produtoView.findViewById(R.id.qtdProdutoCaixaAberto);
            valor = produtoView.findViewById(R.id.valorProduto);
            produtoView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE,R.id.excluir,Menu.NONE,"Excluir");
            menu.add(Menu.NONE,R.id.editar,Menu.NONE,"Editar");

        }
    }
    public void atualiza(List<Produto> produto) {
        this.produtos.clear();
        this.produtos.addAll(produto);
        notifyDataSetChanged();
    }

    public void excluir(Produto produto){
        produtos.remove(produto);
        notifyDataSetChanged();
    }



    public Context getContext() {
        return this.context;
    }

    public Produto getProduto(int posicao) {
        return produtos.get(posicao);
    }

    private int posicao;

    public int getPosicao(){
        return posicao;
    }
    public void setPosicao(int posicao){
        this.posicao = posicao;
    }

}
