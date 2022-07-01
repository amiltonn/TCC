package com.tcc.zipzop.typeconverter;


import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class MoneyConverter {

  public static String toString(Integer value) {
    String valorString = "";
    if(value != null) {
      if (value < 10) {
        valorString = "0,0" + value;
      } else if (value < 100) {
        valorString = "0," + value;
      } else {
        valorString = String.valueOf(value);
        Integer posiVirgula = valorString.length() - 2;
        valorString = valorString.substring(0, posiVirgula) + ',' + valorString.substring(posiVirgula);
      }
    }

    return valorString;
  }
  public static Integer converteParaCentavos(String valor){
    Integer retorno = null;

    Integer quantosAlgarismos = 0;
    Integer quantosAtrasVirgula = 0;
    Boolean ultimoIsVirgula = false;

    for (int i = valor.length() - 1; i >= 0 && quantosAlgarismos < 3; i--) {
      if(valor.charAt(i) == ',' || valor.charAt(i) == '.') {
        if(ultimoIsVirgula.equals(false)) {
          quantosAtrasVirgula = quantosAlgarismos;
          ultimoIsVirgula = true;
        }
      } else if(valor.charAt(i) >= '0' || valor.charAt(i) <= '9') {
        quantosAlgarismos++;
        ultimoIsVirgula = false;
      }
    }

    valor = valor.replaceAll("[\\D]", "");
    retorno = valor.equals("") ? null : Integer.parseInt(valor) * (int)Math.pow(10, 2 - quantosAtrasVirgula);


    return retorno;
  }

}
