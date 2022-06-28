package com.tcc.zipzop.typeconverter;

import android.icu.text.TimeZoneFormat;
import android.util.Log;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeConverter {
  static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @TypeConverter
  public static Date fromTimestamp(String value) {
    if (value != null) {
      try {
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return df.parse(value);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @TypeConverter
  public static String dateToTimestamp(Date date) {
    df.setTimeZone(TimeZone.getTimeZone("UTC"));
    return df.format(date);
  }

}
