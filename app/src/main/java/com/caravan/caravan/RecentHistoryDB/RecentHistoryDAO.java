package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.caravan.caravan.DynamoDB.Table;

import java.util.Date;
import java.util.List;

@Dao
public interface RecentHistoryDAO {
    @Query("select * from recent_history")
    List<RecentHistoryItem> loadRecentHistory();

    @Query("update recent_history set _date = :date, _id = :id, _table = :table  where _id = (select _id from recent_history where _date = (select min(_date) from recent_history))")
    void updateRecentHistory(String id, Date date, Table table);

    @Query("select count(*) from recent_history")
    int getTableSize();

    @Query("delete from recent_history")
    void deleteTable();

    @Query("select count(*) from recent_history where _id=:id")
    int getCountOfRowsWithId(String id);

    @Insert
    void insertItem(RecentHistoryItem recentHistoryItem);
}
