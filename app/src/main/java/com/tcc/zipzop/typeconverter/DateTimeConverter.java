package com.tcc.zipzop.typeconverter;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter {
  static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @TypeConverter
  public static Date fromTimestamp(String value) {
    if (value != null) {
      try {
        return df.parse(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @TypeConverter
  public static String dateToTimestamp(Date date) {
    return df.format(date);
  }

}
