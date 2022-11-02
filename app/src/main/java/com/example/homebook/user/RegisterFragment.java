package com.example.homebook.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        String email = binding.emailAddress.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString();
        String firstname = binding.firstname.getEditText().getText().toString();
        String lastname = binding.lastname.getEditText().getText().toString();
        String confirmPassword = binding.confirmPassword.getEditText().getText().toString();

        if(email.isEmpty()){
            binding.emailAddress.setErrorEnabled(true);
            binding.emailAddress.setError("Email is required!");
            binding.emailAddress.requestFocus();
            return;
        }
        else{
            binding.emailAddress.setErrorEnabled(false);
            binding.emailAddress.clearFocus();
        }
        if(firstname.isEmpty()){
            binding.firstname.setErrorEnabled(true);
            binding.firstname.setError("Firstname is required!");
            binding.firstname.requestFocus();
            return;
        }
        else {
            binding.firstname.setErrorEnabled(false);
            binding.firstname.clearFocus();
        }
        if(lastname.isEmpty()){
            binding.lastname.setErrorEnabled(true);
            binding.lastname.setError("Lastname is required!");
            binding.lastname.requestFocus();
            return;
        }
        else{
            binding.lastname.setErrorEnabled(false);
            binding.lastname.clearFocus();
        }
        if(password.isEmpty()){
            binding.password.setErrorEnabled(true);
            binding.password.setError("Password is required!");
            binding.password.requestFocus();
            return;
        }
        else{
            binding.password.setErrorEnabled(false);
            binding.password.clearFocus();
        }
        if(confirmPassword.isEmpty()){
            binding.confirmPassword.setErrorEnabled(true);
            binding.confirmPassword.setError("Confirm password is required!");
            binding.confirmPassword.requestFocus();
            return;
        }
        else{
            binding.confirmPassword.setErrorEnabled(false);
            binding.confirmPassword.clearFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailAddress.setErrorEnabled(true);
            binding.emailAddress.setError("Please provide valid email!");
            binding.emailAddress.requestFocus();
            return;
        }
        else{
            binding.emailAddress.setErrorEnabled(false);
            binding.emailAddress.clearFocus();
        }
        if(password.length() < 6){
            binding.password.setErrorEnabled(true);
            binding.password.setError("Password needs at least 6 characters");
            binding.password.requestFocus();
            return;
        }
        else{
            binding.password.setErrorEnabled(false);
            binding.password.clearFocus();
        }
        if(!confirmPassword.equals(password)){
            binding.confirmPassword.setErrorEnabled(true);
            binding.confirmPassword.setError("Passwords need to match!");
            binding.confirmPassword.requestFocus();
            return;
        }
        else{
            binding.confirmPassword.setErrorEnabled(false);
            binding.confirmPassword.clearFocus();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(binding != null){
            outState.putString("email", binding.emailAddress.getEditText().getText().toString().trim());
            outState.putString("firstname", binding.firstname.getEditText().getText().toString());
            outState.putString("lastname", binding.lastname.getEditText().getText().toString());
            outState.putString("password", binding.password.getEditText().getText().toString());
            outState.putString("confirm", binding.confirmPassword.getEditText().getText().toString());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null){
            binding.emailAddress.getEditText().setText(savedInstanceState.getString("email"));
            binding.firstname.getEditText().setText(savedInstanceState.getString("firstname"));
            binding.lastname.getEditText().setText(savedInstanceState.getString("lastname"));
            binding.password.getEditText().setText(savedInstanceState.getString("password"));
            binding.confirmPassword.getEditText().setText(savedInstanceState.getString("confirm"));
        }
    }
}