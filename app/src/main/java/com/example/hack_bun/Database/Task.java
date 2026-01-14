package com.example.hack_bun.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tasks",
        foreignKeys = {
                @ForeignKey(entity = Group.class, parentColumns = "groupId", childColumns = "groupId"),
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "createdBy")

        }
)
public class Task {
    @PrimaryKey @NonNull
    public String taskId;
    public String groupId;
    public String title;
    public String description;
    public long deadline;
    public String status;
    public String parentTaskId; // in case of splitting the task into sub-tasks
    public String createdBy;

}
