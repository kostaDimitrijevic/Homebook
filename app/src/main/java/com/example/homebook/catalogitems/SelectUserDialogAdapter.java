package com.example.homebook.catalogitems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.ViewHolderSelectUserBinding;
import com.example.homebook.friends.FriendViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectUserDialogAdapter extends RecyclerView.Adapter<SelectUserDialogAdapter.SelectUserViewHolder> {

    private List<Friend> friendList = new ArrayList<>();
    private FriendViewModel friendViewModel;

    public SelectUserDialogAdapter(FriendViewModel friendViewModel) {
        this.friendViewModel = friendViewModel;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderSelectUserBinding binding = ViewHolderSelectUserBinding.inflate(layoutInflater, parent, false);

        return new SelectUserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectUserViewHolder holder, int position) {
        holder.bind(friendList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class SelectUserViewHolder extends RecyclerView.ViewHolder {

        private ViewHolderSelectUserBinding binding;

        public SelectUserViewHolder(@NonNull ViewHolderSelectUserBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(Friend friend, int position) {
            this.binding.selectUser.setText(friend.getUsername());

            this.binding.checkboxUser.setOnClickListener(v -> {

                if(this.binding.checkboxUser.isChecked()){
                    friendViewModel.getForSendingList().add(friend);
                }
                else{
                    friendViewModel.getForSendingList().remove(friend);
                }

            });
        }
    }
}
