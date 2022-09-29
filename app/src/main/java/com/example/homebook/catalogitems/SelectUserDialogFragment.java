package com.example.homebook.catalogitems;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.frienddata.Friend;
import com.example.homebook.databinding.FragmentSelectUserDialogBinding;
import com.example.homebook.friends.FriendViewModel;
import com.example.homebook.services.DateTimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectUserDialogFragment extends DialogFragment {

    private Callback<String> callback;

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private FragmentSelectUserDialogBinding binding;
    private MainActivity mainActivity;
    private FriendViewModel friendViewModel;

    public SelectUserDialogFragment(Callback<String> callback) {
        this.callback = callback;
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
        binding = FragmentSelectUserDialogBinding.inflate(inflater, container, false);

        SelectUserDialogAdapter adapter = new SelectUserDialogAdapter(friendViewModel);

        friendViewModel.getAcceptedFriends().observe(getViewLifecycleOwner(), adapter::setFriendList);

        binding.selectUserRecyclerView.setAdapter(adapter);
        binding.selectUserRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.selectSendButton.setOnClickListener(view -> {
            for (Friend friend : friendViewModel.getForSendingList()) {
                callback.Invoke(friend.getUsername());
            }

            friendViewModel.getForSendingList().clear();
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}