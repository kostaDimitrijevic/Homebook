package com.example.homebook.data.frienddata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FriendDao {

    @Query("INSERT INTO friend ('username', 'firstname', 'lastname', 'status') VALUES (:username, :firstname, :lastname, :status)")
    long insert(String username, String firstname, String lastname, int status);

    @Query("UPDATE friend SET status=:status WHERE id=:id")
    void updateStatus(int status, long id);

    @Query("DELETE FROM friend WHERE id=:id")
    void delete(long id);

    @Query("SELECT * FROM friend WHERE status=0")
    LiveData<List<Friend>> getPendingFriends();

    @Query("SELECT * FROM friend WHERE status=1")
    LiveData<List<Friend>> getAcceptedFriends();
}
