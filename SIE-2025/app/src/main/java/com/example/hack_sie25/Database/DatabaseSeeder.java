package com.example.hack_sie25.Database;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class DatabaseSeeder implements Callable<Void> {
    private final AppDatabase db;

    public DatabaseSeeder(AppDatabase db) { this.db = db; }

    @Override public Void call() {
        // GROUP
        Group g = new Group();
        g.groupId = "G_1";
        g.nume = "HackSie";
        g.leaderId = "U_1";
        g.createdAt = System.currentTimeMillis();
        db.groupDao().insert(g);

        // USER
        User u1 = new User();
        u1.userId = "U_1";
        u1.nume = "Alex";
        u1.email = "alex@example.com";
        u1.role = "owner";
        u1.groupId = "G_1";
        u1.createdAt = System.currentTimeMillis();
        db.userDao().insert(u1);

        User u2 = new User();
        u2.userId = "U_2";
        u2.nume = "Vlad";
        u2.email = "vlad@example.com";
        u2.role = "member";
        u2.groupId = "G_1";
        u2.createdAt = System.currentTimeMillis();
        db.userDao().insert(u2);

        // TASKS
        Task t1 = new Task();
        t1.taskId = "T_1";
        t1.groupId = "G_1";
        t1.title = "Setup Firebase";
        t1.description = "Connect Android app";
        t1.deadline = System.currentTimeMillis() + 24*60*60*1000L;
        t1.status = "open";
        t1.parentTaskId = null;
        t1.createdBy = "U_1";
        db.taskDao().insert(t1);

        Task t2 = new Task();
        t2.taskId = "T_2";
        t2.groupId = "G_1";
        t2.title = "UI Login";
        t2.description = "Email/Password";
        t2.deadline = System.currentTimeMillis() + 2*24*60*60*1000L;
        t2.status = "open";
        t2.parentTaskId = null;
        t2.createdBy = "U_1";
        db.taskDao().insert(t2);

        // USER ↔ TASK
        User_Tasks ut1 = new User_Tasks();
        ut1.userId = "U_1";
        ut1.taskId = "T_1";
        ut1.assignedAt = System.currentTimeMillis();
        db.userTaskDao().assignUserToTask(ut1);

        User_Tasks ut2 = new User_Tasks();
        ut2.userId = "U_2";
        ut2.taskId = "T_2";
        ut2.assignedAt = System.currentTimeMillis();
        db.userTaskDao().assignUserToTask(ut2);

        return null;
    }
}
