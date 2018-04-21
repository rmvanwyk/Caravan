package com.caravan.caravan.DynamoCacheDB;

import android.arch.persistence.room.TypeConverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class DynamoCacheConverters {
    @TypeConverter
    public String listToString(List<String> stringList) {
        if(stringList == null)
            return null;
        return String.join(",", stringList);
    }

    @TypeConverter
    public List<String> stringToList(String s) {
        if(s == null)
            return null;
        return Arrays.asList(s.split(","));
    }
}
