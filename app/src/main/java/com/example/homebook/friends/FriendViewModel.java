package com.example.homebook.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.data.frienddata.FriendRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FriendViewModel extends ViewModel {

    private FriendRepository friendRepository;
    private SavedStateHandle savedStateHandle;
    private LiveData<List<Friend>> pendingFriends;
    private LiveData<List<Friend>> acceptedFriends;

    @Inject
    public FriendViewModel(FriendRepository friendRepository, SavedStateHandle savedStateHandle) {
        this.friendRepository = friendRepository;
        this.savedStateHandle = savedStateHandle;

        pendingFriends = this.friendRepository.getPendingFriends();
        acceptedFriends = this.friendRepository.getAcceptedFriends();
    }

    public long addFriend(Friend friend){
        return friendRepository.addFriend(friend.getUsername(), friend.getFirstname(), friend.getLastname(), friend.getStatus());
    }

    public void removeFriend(long id){
        friendRepository.delete(id);
    }

    public LiveData<List<Friend>> getPendingFriends(){
        return friendRepository.getPendingFriends();
    }

    public LiveData<List<Friend>> getAcceptedFriends(){
        return friendRepository.getAcceptedFriends();
    }

    public void updateStatus(int status, long id){
        friendRepository.updateStatus(status, id);
    }
}
