package com.example.hack_sie25.Database.DAO;

import androidx.room.*;

import com.example.hack_sie25.Database.User_Tasks;

import java.util.List;

@Dao
public interface UserTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void assignUserToTask(User_Tasks userTask);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void assignUsersToTasks(List<User_Tasks> userTasks);

    @Delete
    void unassignUserFromTask(User_Tasks userTask);

    @Query("SELECT * FROM user_tasks WHERE userId = :userId")
    List<User_Tasks> getTasksForUser(String userId);

    @Query("SELECT * FROM user_tasks WHERE taskId = :taskId")
    List<User_Tasks> getUsersForTask(String taskId);

    @Query("SELECT * FROM user_tasks")
    List<User_Tasks> getAllAssignments();
}
