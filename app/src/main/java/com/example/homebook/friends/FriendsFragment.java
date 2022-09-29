package com.example.homebook.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.firebase.Request;
import com.example.homebook.data.firebase.User;
import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.FragmentFriendsBinding;
import com.example.homebook.services.DateTimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private MainActivity mainActivity;
    private FriendViewModel friendViewModel;

    @Inject
    public ExecutorService executorService;

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

        if(MainActivity.pendingFriends.size() > 0){
            for (int i = MainActivity.readFirebaseRequests; i < MainActivity.pendingFriends.size(); i = ++MainActivity.readFirebaseRequests) {
                Friend friend = MainActivity.pendingFriends.get(i);
                friendViewModel.addFriend(friend);
            }
        }

        if(MainActivity.acceptedFriends.size() > 0){
            for (int i = MainActivity.readFirebaseAccepts; i < MainActivity.acceptedFriends.size(); i = ++MainActivity.readFirebaseAccepts) {
                Friend friend = MainActivity.acceptedFriends.get(i);
                friendViewModel.addFriend(friend);
            }
        }

        FriendsPendingAdaptor friendsPendingAdaptor = new FriendsPendingAdaptor(friendViewModel, this::sendAcceptRequest);
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

        friendViewModel.getPendingFriends().observe(getViewLifecycleOwner(), friends -> {
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

        binding.addNewFriend.setOnClickListener(view -> {
            final EditText input = new EditText(mainActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
            input.setLayoutParams(lp);

            new MaterialAlertDialogBuilder(mainActivity)
                    .setTitle("Insert users email:")
                    .setView(input)
                    .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                        String toUserEmail = input.getText().toString();
                        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        sendRequest(userEmail, toUserEmail);
                    })
                    .show();
        });

        return binding.getRoot();
    }

    private void sendRequest(String from, String to){

        DatabaseReference reference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Request request = new Request(from, to, user.getFirstname(), user.getLastname());
                        DatabaseReference reqReference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("FriendRequests").push();
                        Map<String, Object> map = new HashMap<>();
                        map.put("friend_request", request);
                        reqReference.updateChildren(map).addOnCompleteListener(taskReq -> {
                            if (taskReq.isSuccessful()) {
                                Toast.makeText(mainActivity, "Request sent to user:" + to, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mainActivity, "Error, SENDING FAILED", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
            }
            else {
                Log.d("firebase", "ERROR");
            }
        });

    }

    private void sendAcceptRequest(String to){
        DatabaseReference reference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        Request request = new Request(user.getEmail(), to, user.getFirstname(), user.getLastname());
                        DatabaseReference reqReference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("AcceptRequests").push();
                        Map<String, Object> map = new HashMap<>();
                        map.put("accept_request", request);
                        reqReference.updateChildren(map).addOnCompleteListener(taskReq -> {
                            if (taskReq.isSuccessful()) {
                                Toast.makeText(mainActivity, "You accepted user:" + to, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mainActivity, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
            }
            else {
                Log.d("firebase", "ERROR");
            }
        });
    }
}