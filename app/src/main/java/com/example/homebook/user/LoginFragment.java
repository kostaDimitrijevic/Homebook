package com.example.homebook.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homebook.BottomNavigationUtil;
import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {


    private MainActivity mainActivity;
    private FragmentLoginBinding binding;
    private NavController navController;
    private FirebaseAuth mAuth;

    public LoginFragment() {
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
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.loginButton.setOnClickListener(
                view -> {
                    login();
                }
        );

        binding.gotoRegister.setOnClickListener(
            view -> {
                NavDirections action = LoginFragmentDirections.actionRegister();
                navController.navigate(action);
            }
        );

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(savedInstanceState != null){
            binding.emailAddress.getEditText().setText(savedInstanceState.getString("email"));
            binding.password.getEditText().setText(savedInstanceState.getString("password"));
        }

    }

    private void login() {

        String email = binding.emailAddress.getEditText().getText().toString().trim();
        String password = binding.password.getEditText().getText().toString();

        if(email.isEmpty()){
            binding.emailAddress.setError("Email is required!");
            binding.emailAddress.requestFocus();
            return;
        }

        if(password.isEmpty()){
            binding.password.setError("Password is required!");
            binding.password.requestFocus();
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

        binding.loginProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(mainActivity, "SUCCESSFUL LOGIN", Toast.LENGTH_SHORT).show();
                        NavController navController = BottomNavigationUtil.changeNavHostFragment(R.id.bottom_navigation_category);
                    }
                    else{
                        Toast.makeText(mainActivity, "FAILED", Toast.LENGTH_SHORT).show();
                    }

                    binding.loginProgressBar.setVisibility(View.GONE);
                });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(binding != null){
            outState.putString("email", binding.emailAddress.getEditText().getText().toString().trim());
            outState.putString("password", binding.password.getEditText().getText().toString());
        }
    }
}
