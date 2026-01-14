package com.example.hack_bun.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonBlocking;

@Entity(tableName="users")
public class User {
    @PrimaryKey @NonNull
    public String userId;
    public String nume;
    public String email;
    public String role; //  within a group
    public String groupId;
    public long createdAt;
}
