package com.example.homebook.friends;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.firebase.Request;
import com.example.homebook.data.firebase.User;
import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.ViewHolderFriendsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsPendingAdaptor extends RecyclerView.Adapter<FriendsPendingAdaptor.FriendViewHolder> {

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private List<Friend> pendingFriends = new ArrayList<>();
    private FriendViewModel friendViewModel;
    private Callback<String> callback;

    public FriendsPendingAdaptor(FriendViewModel friendViewModel, Callback<String> callback) {
        this.friendViewModel = friendViewModel;
        this.callback = callback;
    }

    public void setPendingFriends(List<Friend> pendingFriends) {
        this.pendingFriends = pendingFriends;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderFriendsBinding binding = ViewHolderFriendsBinding.inflate(layoutInflater, parent, false);

        return new FriendViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.bind(pendingFriends.get(position), position);
    }

    @Override
    public int getItemCount() {
        return pendingFriends.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderFriendsBinding binding;

        public FriendViewHolder(@NonNull ViewHolderFriendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Friend friend, int position) {
            this.binding.friendUsername.setText(friend.getUsername());
            this.binding.friendName.setText(friend.getFirstname());
            this.binding.friendLast.setText(friend.getLastname());

            this.binding.buttonAcceptFriend.setOnClickListener(v -> {
                friendViewModel.updateStatus(1, friend.getId());
                pendingFriends.remove(position);
                notifyItemRemoved(position);
                callback.Invoke(friend.getUsername());
            });
        }
    }
}
