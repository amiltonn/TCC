package com.tcc.zipzop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tcc.zipzop.adapter.ItemCaixaAdapterActivity;
import com.tcc.zipzop.asynctask.ListarItemTask;
import com.tcc.zipzop.database.ZipZopDataBase;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;
import com.tcc.zipzop.entity.NaoEntityNomeProvisorioItemDoCaixa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AbrirCaixaActivity extends AppCompatActivity {
    private AppCompatButton ButtonAbrirCaixa;
    private EditText quantidadeItens;

    //Itens do Caixa
    private ListView listarItens;
    private List<NaoEntityNomeProvisorioItemDoCaixa> listaItensDoCaixa;
    private ItemCaixaAdapterActivity itemCaixaAdapterActivity;

    private Spinner spinnerItens;
    List<Item> listaItens;
    private ItemDAO itensCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Actionbar);
        setContentView(R.layout.activity_abrir_caixa);

        //spinner
        ZipZopDataBase dataBase = ZipZopDataBase.getInstance(this);
        itensCtrl = dataBase.getItemDAO();

        try {
            listaItens = new ListarItemTask(itensCtrl).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.spinnerItens = (Spinner) this.findViewById(R.id.SpnItem);
        ArrayAdapter<Item> spnItemAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_dropdown_item_1line, this.listaItens);
        this.spinnerItens.setAdapter(spnItemAdapter);
        //end spinner

        this.quantidadeItens = (EditText) this.findViewById(R.id.Quantidade);

        // variaveis e objetos dos itens do caixa
        this.listarItens = (ListView) this.findViewById(R.id.lsvItens);
        this.listaItensDoCaixa = new ArrayList<>();
        this.itemCaixaAdapterActivity = new ItemCaixaAdapterActivity(AbrirCaixaActivity.this, this.listaItensDoCaixa);
        this.listarItens.setAdapter(this.itemCaixaAdapterActivity);

        //Função do botão
        ButtonAbrirCaixa = findViewById(R.id.Bt_AbrirCaixa);
        ButtonAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbrirCaixaActivity.this,CaixaActivity.class);

                startActivity(intent);

            }
        });


    }

    public void eventAddItem (View view) {
        NaoEntityNomeProvisorioItemDoCaixa itemDoCaixa = new NaoEntityNomeProvisorioItemDoCaixa();

        Item itemSelecionado = (Item) this.spinnerItens.getSelectedItem();

        int quantidadeItem = 0;
        if(this.quantidadeItens.getText().toString().equals("")){
            quantidadeItem = 1;
        }else {
            quantidadeItem = Integer.parseInt(this.quantidadeItens.getText().toString());
        }

        itemDoCaixa.setNome(itemSelecionado.getNome());
        itemDoCaixa.setQtdSelecionada(quantidadeItem);

        this.itemCaixaAdapterActivity.addItemCaixa(itemDoCaixa);
    }
}