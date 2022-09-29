package com.example.homebook.data.frienddata;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class FriendRepository {
    private FriendDao friendDao;
    private ExecutorService executorService;

    @Inject
    public FriendRepository(FriendDao friendDao, ExecutorService executorService) {
        this.friendDao = friendDao;
        this.executorService = executorService;
    }

    public long addFriend(String username, String firstname, String lastname, int status){
        Future<Long> id = executorService.submit(() -> friendDao.insert(username, firstname, lastname, status));

        try {
            return id.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateStatus(int status, long id){
        executorService.submit(() -> {
            friendDao.updateStatus(status, id);
        });
    }

    public void delete(long id){
        executorService.submit(() -> {
            friendDao.delete(id);
        });
    }

    public LiveData<List<Friend>> getPendingFriends(){
        return friendDao.getPendingFriends();
    }

    public LiveData<List<Friend>> getAcceptedFriends(){
        return friendDao.getAcceptedFriends();
    }
}
