package com.tcc.zipzop.asynctask;
import android.os.AsyncTask;
import android.util.Log;

import com.tcc.zipzop.adapter.ItemAdapterActivity;
import com.tcc.zipzop.database.dao.ItemDAO;
import com.tcc.zipzop.entity.Item;

public class ExcluirItemTask extends AsyncTask<Void, Void, Void> {

    private final ItemDAO dao;
    private final ItemAdapterActivity adapter;
    private final Item item;

    public ExcluirItemTask(
            ItemDAO dao,
            ItemAdapterActivity adapter,
            Item item
    ){
        this.dao = dao;
        this.adapter = adapter;
        this.item = item;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deletar(item.getId());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.excluir(item);
    }

}
