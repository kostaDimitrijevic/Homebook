package com.example.homebook.friends;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.ViewHolderFriendsBinding;
import com.example.homebook.services.DateTimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsAcceptedAdapter extends RecyclerView.Adapter<FriendsAcceptedAdapter.FriendsViewHolder> {

    private List<Friend> acceptedFriends = new ArrayList<>();
    private final FriendViewModel friendViewModel;
    private final MainActivity mainActivity;

    public FriendsAcceptedAdapter(FriendViewModel friendViewModel, MainActivity mainActivity) {
        this.friendViewModel = friendViewModel;
        this.mainActivity = mainActivity;
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

            this.binding.buttonDeleteFriend.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(mainActivity)
                        .setTitle("ARE YOU SURE THAT YOU WANT TO REMOVE FRIEND:" + friend.getUsername())
                        .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                            // nothing happens
                        })
                        .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                            // nothing happens
                        })
                        .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                            friendViewModel.removeFriend(friend.getId());
                            acceptedFriends.remove(position);
                            notifyItemRemoved(position);
                        })
                        .show();

            });
        }
    }
}
