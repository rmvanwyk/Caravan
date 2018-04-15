package com.caravan.caravan.RecentHistoryDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface RecentHistoryDAO {
    @Query("select * from recent_history")
    List<RecentHistoryItem> loadRecentHistory();

    @Query("update recent_history set search_date = :date, id = :id  where id = (select id from recent_history where search_date = (select min(search_date) from recent_history))")
    void updateRecentHistory(int id, Date date);

    @Query("select count(*) from recent_history")
    int getTableSize();

    @Query("delete from recent_history")
    void deleteTable();

    @Insert
    void insertItem(RecentHistoryItem recentHistoryItem);
}
