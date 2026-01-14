package com.example.hack_sie25.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "logs")
public class LogEntry {
    @PrimaryKey @NonNull
    public String id;
    public String taskId;
    public String action; // CREATED, UPDATED, STATUS_CHANGE
    public long timestamp;
    public String actorUserId;

    public static LogEntry of(String id, String taskId, String action, long ts, String actor){
        LogEntry l = new LogEntry();
        l.id = id;
        l.taskId = taskId;
        l.action = action;
        l.timestamp = ts;
        l.actorUserId = actor;
        return l;
    }
}
