package com.caravan.caravan.RecentHistoryDB;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableSet;
import android.support.test.runner.AndroidJUnit4;

import com.caravan.caravan.DynamoDB.Table;

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
        database = RecentHistoryDatabase.getInMemoryInstance(InstrumentationRegistry.getTargetContext());
        dao = database.recentHistoryDAO();
    }

    @Test
    public void testSize() {
        final int exptectedTableSize = 8;
        dao.deleteTable();
        dao.insertItem(new RecentHistoryItem("1", new Date(), Table.blueprint));
        dao.insertItem(new RecentHistoryItem("2", new Date(), Table.location));
        dao.insertItem(new RecentHistoryItem("3", new Date(), Table.city));
        dao.insertItem(new RecentHistoryItem("4", new Date(), Table.blueprint));
        dao.insertItem(new RecentHistoryItem("5", new Date(), Table.blueprint));
        dao.insertItem(new RecentHistoryItem("6", new Date(), Table.blueprint));
        dao.insertItem(new RecentHistoryItem("7", new Date(), Table.blueprint));
        dao.insertItem(new RecentHistoryItem("8", new Date(), Table.location));
        assertEquals(exptectedTableSize, dao.getTableSize());
    }

    @Test
    public void testUpdate() {
        boolean outputIsExpected = true;
        final Set<String> expectedOutputIds = ImmutableSet.<String>builder().add("4").add("5").add("6").add("7").build();
        dao.deleteTable();
        dao.insertItem(new RecentHistoryItem("1", new Date(1), Table.location));
        dao.insertItem(new RecentHistoryItem("2", new Date(2), Table.location));
        dao.insertItem(new RecentHistoryItem("3", new Date(3), Table.city));
        dao.insertItem(new RecentHistoryItem("4", new Date(4), Table.location));
        dao.updateRecentHistory("5", new Date(5), Table.blueprint);
        dao.updateRecentHistory("6", new Date(6), Table.blueprint);
        dao.updateRecentHistory("7", new Date(7), Table.blueprint);

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
        database.destroyInMemoryInstance();
    }
}
