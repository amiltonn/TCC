package com.tcc.zipzop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tcc.zipzop.database.dao.CaixaDAO;
import com.tcc.zipzop.database.dao.CaixaFundoDAO;
import com.tcc.zipzop.database.dao.CaixaProdutoDAO;
import com.tcc.zipzop.database.dao.EstoqueDAO;
import com.tcc.zipzop.database.dao.EstoqueProdutoDAO;
import com.tcc.zipzop.database.dao.FormaPagamentoDAO;
import com.tcc.zipzop.database.dao.FormulaDAO;
import com.tcc.zipzop.database.dao.GastoDAO;
import com.tcc.zipzop.database.dao.GastoProdutoDAO;
import com.tcc.zipzop.database.dao.InsumoDAO;
import com.tcc.zipzop.database.dao.ProdutoDAO;
import com.tcc.zipzop.database.dao.UnidadeMedidaDAO;
import com.tcc.zipzop.database.dao.VendaDAO;
import com.tcc.zipzop.database.dao.VendaLocalDAO;
import com.tcc.zipzop.database.dao.VendaProdutoDAO;
import com.tcc.zipzop.entity.*;
import com.tcc.zipzop.typeconverter.DateTimeConverter;

@Database(version = 3,
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
    public abstract CaixaFundoDAO getCaixaFundoDAO();
    public abstract CaixaProdutoDAO getCaixaProdutoDAO();
    public abstract EstoqueDAO getEstoqueDAO();
    public abstract EstoqueProdutoDAO getEstoqueProdutoDAO();
    public abstract FormaPagamentoDAO getFormaPagamentoDAO();
    public abstract FormulaDAO getFormulaDAO();
    public abstract GastoDAO getGastoDAO();
    public abstract GastoProdutoDAO getGastoProdutoDAO();
    public abstract InsumoDAO getInsumoDAO();
    public abstract VendaDAO getVendaDAO();
    public abstract VendaLocalDAO getVendaLocalDAO();
    public abstract VendaProdutoDAO getVendaProdutoDAO();


    public static ZipZopDataBase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ZipZopDataBase.class, "zipzop.db")
                .addCallback(ZipZopCallbacks.callbacks)
                .addMigrations(ZipZopMigrations.TODAS_MIGRATIONS)
                .build();
    }
}
