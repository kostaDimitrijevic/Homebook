package com.example.homebook.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homebook.BottomNavigationUtil;
import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.firebase.User;
import com.example.homebook.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    private MainActivity mainActivity;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        binding.registerButton.setOnClickListener(view -> {
            registerUser();
        });

        return binding.getRoot();
    }

    private void registerUser(){
        String email = binding.emailAddress.getEditText().getText().toString();
        String password = binding.password.getEditText().getText().toString();
        String firstname = binding.firstname.getEditText().getText().toString();
        String lastname = binding.lastname.getEditText().getText().toString();
        String confirmPassword = binding.confirmPassword.getEditText().getText().toString();

        if(email.isEmpty()){
            binding.emailAddress.setError("Email is required!");
            binding.emailAddress.requestFocus();
            return;
        }
        if(firstname.isEmpty()){
            binding.firstname.setError("Firstname is required!");
            binding.firstname.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            binding.lastname.setError("Lastname is required!");
            binding.lastname.requestFocus();
            return;
        }
        if(password.isEmpty()){
            binding.password.setError("Password is required!");
            binding.password.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            binding.confirmPassword.setError("Confirm password is required!");
            binding.confirmPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailAddress.setError("Please provide valid email!");
            binding.emailAddress.requestFocus();
            return;
        }

        if(password.length() < 6){
            binding.password.setError("Password needs at least 6 characters");
            binding.password.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)){
            binding.confirmPassword.setError("Passwords need to match!");
            binding.confirmPassword.requestFocus();
            return;
        }

        binding.registerProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(mainActivity, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "REGISTRATION COMPLETED", Toast.LENGTH_SHORT).show();
                        User user = new User(email, firstname, lastname);
                        FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                                        .child(FirebaseAuth.getInstance()
                                        .getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(mainActivity, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(mainActivity, "User registration error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        BottomNavigationUtil.changeNavHostFragment(R.id.bottom_navigation_category);
                    }
                    else{
                        Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
                    }
                    binding.registerProgressBar.setVisibility(View.GONE);
                });
    }
}