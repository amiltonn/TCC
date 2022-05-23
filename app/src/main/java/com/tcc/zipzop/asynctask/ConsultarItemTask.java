package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.activity.SalvarItemActivity;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

import java.util.function.LongFunction;

public class ConsultarItemTask extends AsyncTask <Void, Void, Item> {

    private final ItemDAO dao;
    private final Long id;

    public ConsultarItemTask(
            ItemDAO dao,
            Long id
    ){
            this.dao = dao;
            this.id = id;
    }


    @Override
    protected Item doInBackground(Void... voids) {
        return dao.consultar(this.id);
    }

//    @Override
//    protected void onPostExecute(Item item) {
//        super.onPostExecute(item);
//    }
}
