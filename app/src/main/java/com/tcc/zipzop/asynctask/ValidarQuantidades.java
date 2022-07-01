package com.tcc.zipzop.asynctask;

import android.os.AsyncTask;

public class ValidarQuantidades extends AsyncTask<Void, Void, Boolean> {
  private final Integer qtdItemPai;
  private final Integer qtdItemFilho;

  public ValidarQuantidades(Integer qtdItemPai, Integer qtdItemFilho) {
    this.qtdItemPai = qtdItemPai;
    this.qtdItemFilho = qtdItemFilho;
  }

  @Override
  protected Boolean doInBackground(Void... voids) {
    return qtdItemPai >= qtdItemFilho;
  }
}
