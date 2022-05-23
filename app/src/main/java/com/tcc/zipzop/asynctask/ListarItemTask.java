package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.activity.SalvarItemActivity;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.List;
import java.util.function.LongFunction;

public class ListarItemTask extends AsyncTask <Void, Void, List<Item>> {

    private final ItemDAO dao;

    public ListarItemTask(
            ItemDAO dao
    ){
            this.dao = dao;
    }


    @Override
    protected List<Item> doInBackground(Void... voids) {
        return dao.listar();
    }

//    @Override
//    protected void onPostExecute(List<Item> itens) {
//        super.onPostExecute(itens);
//    }
}
