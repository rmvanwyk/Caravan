package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.caravan.caravan.DynamoDB.Table;

import java.util.Date;

import lombok.Data;

@Data
@Entity(tableName = "recent_history")
public class RecentHistoryItem {
    @ColumnInfo(name="_id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name="_date")
    private Date date;

    @ColumnInfo(name="_table")
    private Table table;

    public RecentHistoryItem(String id, Date date, Table table) {
        this.id = id;
        this.date = date;
        this.table = table;
    }
}
