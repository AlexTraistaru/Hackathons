package com.example.hack_bun.Database.DAO;

import androidx.room.*;

import com.example.hack_bun.Database.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Group group);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Group> groupsList);

    @Update
    void update(Group group);

    @Delete
    void delete(Group group);

    @Query("SELECT * FROM `groups` WHERE groupId = :groupId")
    Group getGroupById(String groupId);

    @Query("SELECT * FROM `groups`")
    List<Group> getAllGroups();

    @Query("SELECT * FROM `groups` WHERE leaderId = :leaderId")
    List<Group> getGroupsByLeader(String leaderId);
}
