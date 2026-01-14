package com.example.hack_sie25;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hack_sie25.Database.AppDatabase;
import com.example.hack_sie25.Database.DatabaseSeeder;
import com.example.hack_sie25.Database.Group;
import com.example.hack_sie25.Database.LogEntry;
import com.example.hack_sie25.Database.Task;
import com.example.hack_sie25.Database.User;
import com.example.hack_sie25.ui.TaskAdapter;
import com.example.hack_sie25.util.Crypto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppDatabase db;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        // seed once
        SharedPreferences prefs = getSharedPreferences("seed", MODE_PRIVATE);
        if (!prefs.getBoolean("done", false)) {
            new DatabaseSeeder(db).call();
            prefs.edit().putBoolean("done", true).apply();
        }

        RecyclerView rv = findViewById(R.id.rvTasks);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(t -> {
            Toast.makeText(this, "Task: " + t.title, Toast.LENGTH_SHORT).show();
        });
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> showAddDialog());

        refresh();
    }

    private void refresh(){
        List<Task> tasks = db.taskDao().getAllTasks();
        adapter.setItems(tasks);
    }

    private void showAddDialog(){
        EditText input = new EditText(this);
        input.setHint("Titlu task");
        new AlertDialog.Builder(this)
                .setTitle("Task nou")
                .setView(input)
                .setPositiveButton("Save", (d, w) -> {
                    String title = input.getText().toString().trim();
                    if(title.isEmpty()){ return; }
                    Task t = new Task();
                    t.taskId = "T_" + UUID.randomUUID().toString().substring(0,8);
                    t.groupId = "G_1";
                    t.title = title;
                    t.description = "Descriere";
                    t.status = "open";
                    t.deadline = System.currentTimeMillis() + 24*60*60*1000L;
                    t.createdBy = "U_1";
                    t.encryptedNote = Crypto.encrypt("notita secreta");
                    db.taskDao().insert(t);

                    LogEntry le = LogEntry.of("L_"+UUID.randomUUID().toString().substring(0,8), t.taskId, "CREATED", System.currentTimeMillis(), "U_1");
                    db.logDao().insert(le);
                    refresh();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
