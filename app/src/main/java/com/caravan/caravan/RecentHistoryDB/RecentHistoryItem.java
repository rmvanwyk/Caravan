package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Data
@Entity(tableName = "recent_history")
public class RecentHistoryItem {
    @ColumnInfo(name="id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name="search_date")
    private long date;

    public RecentHistoryItem(int id, long date) {
        this.id = id;
        this.date = date;
    }
}
