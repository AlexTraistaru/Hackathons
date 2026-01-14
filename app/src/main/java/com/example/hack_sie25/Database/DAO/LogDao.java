package com.example.hack_sie25.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import com.example.hack_sie25.Database.LogEntry;

@Dao
public interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LogEntry log);

    @Query("SELECT * FROM logs WHERE taskId = :taskId ORDER BY timestamp DESC")
    List<LogEntry> getLogsForTask(String taskId);
}
