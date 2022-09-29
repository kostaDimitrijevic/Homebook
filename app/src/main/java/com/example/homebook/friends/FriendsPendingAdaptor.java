package com.example.homebook.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.ViewHolderFriendsBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendsPendingAdaptor extends RecyclerView.Adapter<FriendsPendingAdaptor.FriendViewHolder> {

    private List<Friend> pendingFriends = new ArrayList<>();
    private FriendViewModel friendViewModel;

    public FriendsPendingAdaptor(FriendViewModel friendViewModel) {
        this.friendViewModel = friendViewModel;
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
            });
        }
    }
}
