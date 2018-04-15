package com.caravan.caravan.RecentHistoryDB;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableSet;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class RecentHistoryDatabaseTest {
    RecentHistoryDAO dao;
    RecentHistoryDatabase database;

    @Before
    public void initializeDatabase() {
        database = RecentHistoryDatabase.getInstance(InstrumentationRegistry.getTargetContext());
        dao = database.recentHistoryDAO();
    }

    private void hold(){
        for(int i=0; i < 10000; i++) {
            i++;
            i--;
        }
    }

    @Test
    public void testSize() {
        final int exptectedTableSize = 8;
        dao.deleteTable();
        dao.insertItem(new RecentHistoryItem(1, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(2,  (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(3, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(4, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(5, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(6,  (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(7, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(8, (new Date()).getTime()));
        assertEquals(exptectedTableSize, dao.getTableSize());
    }

    @Test
    public void testUpdate() {
        boolean outputIsExpected = true;
        final Set<Integer> expectedOutputIds = ImmutableSet.<Integer>builder().add(4).add(5).add(6).add(7).build();
        dao.deleteTable();
        dao.insertItem(new RecentHistoryItem(1, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(2,  (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(3, (new Date()).getTime()));
        dao.insertItem(new RecentHistoryItem(4, (new Date()).getTime()));
        dao.updateRecentHistory(5, new Date());
        dao.updateRecentHistory(6, new Date());
        dao.updateRecentHistory(7, new Date());

        List<RecentHistoryItem> searchHistory = dao.loadRecentHistory();
        for (RecentHistoryItem item : searchHistory) {
            if(!expectedOutputIds.contains(item.getId())){
                outputIsExpected = false;
            }
        }
        assertTrue(outputIsExpected);
    }

    @After
    public void destroyDatabase() {
        database.close();
    }
}
