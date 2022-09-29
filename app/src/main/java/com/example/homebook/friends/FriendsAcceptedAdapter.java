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

public class FriendsAcceptedAdapter extends RecyclerView.Adapter<FriendsAcceptedAdapter.FriendsViewHolder> {

    private List<Friend> acceptedFriends = new ArrayList<>();
    private FriendViewModel friendViewModel;

    public FriendsAcceptedAdapter(FriendViewModel friendViewModel) {
        this.friendViewModel = friendViewModel;
    }

    public void setAcceptedFriends(List<Friend> acceptedFriends) {
        this.acceptedFriends = acceptedFriends;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderFriendsBinding binding = ViewHolderFriendsBinding.inflate(layoutInflater, parent, false);

        return new FriendsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.bind(acceptedFriends.get(position), position);
    }

    @Override
    public int getItemCount() {
        return acceptedFriends.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderFriendsBinding binding;

        public FriendsViewHolder(@NonNull ViewHolderFriendsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Friend friend, int position) {
            this.binding.buttonAcceptFriend.setVisibility(View.GONE);
            this.binding.buttonRejectFriend.setVisibility(View.GONE);
            this.binding.buttonDeleteFriend.setVisibility(View.VISIBLE);

            this.binding.friendUsername.setText(friend.getUsername());
            this.binding.friendName.setText(friend.getFirstname());
            this.binding.friendLast.setText(friend.getLastname());
        }
    }
}
