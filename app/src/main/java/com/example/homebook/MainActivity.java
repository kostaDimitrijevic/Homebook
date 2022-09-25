package com.example.homebook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homebook.data.firebase.Catalog;
import com.example.homebook.databinding.ActivityMainBinding;
import com.example.homebook.services.FirebaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String INTENT_ACTION_NOTIFICATION = "com.example.runningapplication.NOTIFICATION";

    public static List<Catalog> firebaseCatalogList = new ArrayList<>();

    public static int readFirebaseCatalogs = 0;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent();
        intent.setClass(this, FirebaseService.class);
        this.startService(intent);

        if(savedInstanceState == null){
            setupBottomNavigation();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.bottomNavigation.setVisibility(View.VISIBLE);
        setupBottomNavigation();
    }

    private void setupBottomNavigation(){
        int[] navResourceIds = new int[]{
            R.navigation.nav_graph_login,
            R.navigation.nav_graph_category,
            R.navigation.nav_graph_catalog,
            R.navigation.nav_graph_analytics
        };

        BottomNavigationUtil.setup(
            binding.bottomNavigation,
            getSupportFragmentManager(),
            navResourceIds,
            R.id.nav_host_conatiner
        );
    }
}