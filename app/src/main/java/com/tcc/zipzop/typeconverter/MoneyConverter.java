package com.tcc.zipzop.typeconverter;


import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class MoneyConverter {

  @TypeConverter
  public static BigDecimal fromInteger(Integer value) {
    BigDecimal scaledAmount = new BigDecimal(value);
    scaledAmount.setScale(2);
    return scaledAmount;
  }

  @TypeConverter
  public static Integer moneyToInteger(BigDecimal money) {
    money.setScale(0);
    return money.intValue();
  }
}
