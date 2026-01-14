package com.example.hack_sie25.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class Group {
    @PrimaryKey @NonNull
    public String groupId;
    public String nume;
    public String leaderId;
    public long createdAt;
}
