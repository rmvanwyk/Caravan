package com.caravan.caravan.RecentHistoryDB;


import android.arch.persistence.room.TypeConverter;

import com.caravan.caravan.DynamoDB.Table;

import java.util.Date;

public class RecentHistoryConverters {
    @TypeConverter
    public static Date dateFromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Table tableFromString(String s) {
        return Table.valueOf(s);
    }

    @TypeConverter
    public static String tableToString(Table table){
        return table.name();
    }
}
