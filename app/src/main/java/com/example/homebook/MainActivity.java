package com.example.homebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.homebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(savedInstanceState == null){
            setupBottomNavigation();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setupBottomNavigation();
    }

    private void setupBottomNavigation(){
        int[] navResourceIds = new int[]{
            R.navigation.nav_graph_login,
            R.navigation.nav_graph_category
        };

        BottomNavigationUtil.setup(
                binding.bottomNavigation,
                getSupportFragmentManager(),
                navResourceIds,
                R.id.nav_host_conatiner
        );
    }
}