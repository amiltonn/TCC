package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.database.dao.UnidadeMedidaDAO;
import com.tcc.zipzop.entity.*;
import com.tcc.zipzop.typeconverter.DateTimeConverter;

@Database(version = 1,
            entities = {
                OperationActive.class,
                UnidadeMedida.class,
                Formula.class,
                Produto.class,
                Gasto.class,
                GastoProduto.class,
                Insumo.class,
                Estoque.class,
                EstoqueProduto.class,
                Caixa.class,
                CaixaFundo.class,
                CaixaProduto.class,
                VendaLocal.class,
                FormaPagamento.class,
                Venda.class,
                VendaProduto.class
            },
            exportSchema = false)
@TypeConverters({DateTimeConverter.class})
public abstract class ZipZopDataBase extends RoomDatabase {
    public abstract ProdutoDAO getProdutoDAO();
    public abstract UnidadeMedidaDAO getUnidadeMedidaDAO();
    public abstract CaixaDAO getCaixaDAO();

    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(ZipZopCallbacks.callbacks)
                .build();
    }
}
