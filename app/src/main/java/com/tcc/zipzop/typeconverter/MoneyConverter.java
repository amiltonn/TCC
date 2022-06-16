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

  public static String toString(Integer value) {
    String valorString = "";
    if(value < 10)
      valorString = "0,0" + value;
    else if (value < 100)
      valorString = "0," + value;
    else {
      valorString = String.valueOf(value);
      Integer posiVirgula = valorString.length() - 2;
      valorString = valorString.substring(0, posiVirgula) + ',' + valorString.substring(posiVirgula);
    }


    return valorString;
  }
}
