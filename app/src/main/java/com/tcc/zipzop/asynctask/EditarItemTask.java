package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

import com.tcc.zipzop.activity.SalvarItemActivity;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

public class EditarItemTask extends AsyncTask<Void, Void, Void> {

    private final ItemDAO dao;
    private final SalvarItemActivity salvarItemActivity;
    private final Item item;

    public EditarItemTask(
            ItemDAO dao,
            SalvarItemActivity salvarItemActivity,
            Item item
    ){
        this.dao = dao;
        this.salvarItemActivity = salvarItemActivity;
        this.item = item;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.alterar(item);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        salvarItemActivity.salvarComSucesso();
    }

}
