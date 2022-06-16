package com.tcc.zipzop.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OperationActive {

  @PrimaryKey

  @ColumnInfo(defaultValue = "1")
  @NonNull
  private Integer recursionLayer = 1;

  public Integer getRecursionLayer() {
    return recursionLayer;
  }

  public void setRecursionLayer(Integer recursionLayer) {
    this.recursionLayer = recursionLayer;
  }

}
