package com.example.hack_sie25.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "user_tasks",
        primaryKeys = {"userId", "taskId"}
)
public class User_Tasks {
    @NonNull
    public String userId;
    @NonNull
    public String taskId;
    public long assignedAt;
}
