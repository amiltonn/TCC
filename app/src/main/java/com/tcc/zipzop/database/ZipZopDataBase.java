package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.entity.*;

@Database(version = 1,
            entities = {
                UnidadeMedida.class,
                Formula.class,
                Produto.class,
//                Gasto.class,
//                GastoProduto.class,
//                Insumo.class,
//                Estoque.class,
//                EstoqueProduto.class,
                Caixa.class,
//                CaixaFundo.class,
//                CaixaProduto.class,
//                VendaLocal.class,
//                FormaPagamento.class,
//                Venda.class,
//                VendaProduto.class
            },
            exportSchema = false)
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ProdutoDAO getProdutoDAO();
    public abstract CaixaDAO getCaixaDAO();

    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(ZipZopCallbacks.callbacks)
                .build();
    }
}
