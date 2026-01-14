package com.example.hack_bun.Database.DAO;

import androidx.room.*;
import java.util.List;

import com.example.hack_bun.Database.Task;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks WHERE taskId = :taskId")
    Task getTaskById(String taskId);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks WHERE groupId = :groupId")
    List<Task> getTasksByGroup(String groupId);

    @Query("SELECT * FROM tasks WHERE parentTaskId = :parentTaskId")
    List<Task> getSubtasks(String parentTaskId);

    @Query("SELECT * FROM tasks WHERE status = :status")
    List<Task> getTasksByStatus(String status);
}
