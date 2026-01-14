package com.example.hack_sie25.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hack_sie25.Database.DAO.GroupDao;
import com.example.hack_sie25.Database.DAO.TaskDao;
import com.example.hack_sie25.Database.DAO.UserDao;
import com.example.hack_sie25.Database.DAO.UserTaskDao;
import com.example.hack_sie25.Database.DAO.LogDao;
import com.example.hack_sie25.Database.LogEntry;

@Database(
        entities = {User.class, Task.class, Group.class, User_Tasks.class, LogEntry.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LogDao logDao();
    public abstract UserDao userDao();
    public abstract TaskDao taskDao();
    public abstract GroupDao groupDao();
    public abstract UserTaskDao userTaskDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    ctx.getApplicationContext(),
                                    AppDatabase.class,
                                    "tasks.db"       // numele bazei
                            )
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
