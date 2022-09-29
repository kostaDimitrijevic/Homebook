package com.example.homebook.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.MainActivity;
import com.example.homebook.databinding.FragmentFriendsBinding;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private MainActivity mainActivity;
    private FriendViewModel friendViewModel;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        friendViewModel = new ViewModelProvider(mainActivity).get(FriendViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        FriendsPendingAdaptor friendsPendingAdaptor = new FriendsPendingAdaptor(friendViewModel);
        FriendsAcceptedAdapter friendsAcceptedAdapter = new FriendsAcceptedAdapter(friendViewModel);

        friendViewModel.getAcceptedFriends().observe(getViewLifecycleOwner(), friends -> {
            if(friends.size() == 0){
                this.binding.yourFriends.setVisibility(View.GONE);
            }
            else{
                this.binding.yourFriends.setVisibility(View.VISIBLE);
                friendsAcceptedAdapter.setAcceptedFriends(friends);
            }
        });

        friendViewModel.getAcceptedFriends().observe(getViewLifecycleOwner(), friends -> {
            if(friends.size() == 0){
                this.binding.pendingFriends.setVisibility(View.GONE);
            }
            else{
                this.binding.pendingFriends.setVisibility(View.VISIBLE);
                friendsPendingAdaptor.setPendingFriends(friends);
            }
        });

        binding.yourFriendsRecyclerView.setAdapter(friendsAcceptedAdapter);
        binding.yourFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.pendingFriendsRecyclerView.setAdapter(friendsPendingAdaptor);
        binding.pendingFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        return binding.getRoot();
    }
}