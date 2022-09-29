package com.example.homebook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.homebook.catalog.CatalogFragmentDirections;
import com.example.homebook.data.firebase.Catalog;
import com.example.homebook.data.frienddata.Friend;
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
    public static final String INTENT_ACTION_NOTIFICATION = "com.example.homebook.NOTIFICATION";
    public static final String INTENT_ACTION_REQUEST_NOTIFICATION = "com.example.homebook.REQUEST_NOTIFICATION";

    public static List<Catalog> firebaseCatalogList = new ArrayList<>();
    public static List<Friend> pendingFriends = new ArrayList<>();
    public static List<Friend> acceptedFriends = new ArrayList<>();

    public static int readFirebaseCatalogs = 0;
    public static int readFirebaseRequests = 0;
    public static int readFirebaseAccepts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(savedInstanceState == null){
            BottomNavigationUtil.login_passed = false;
            setupBottomNavigation();
        }

        if(getIntent().getAction().equals(INTENT_ACTION_NOTIFICATION)){
            NavController navController = BottomNavigationUtil.changeNavHostFragment(R.id.bottom_navigation_catalog);
        }
        else if(getIntent().getAction().equals(INTENT_ACTION_REQUEST_NOTIFICATION)){
            NavController navController = BottomNavigationUtil.changeNavHostFragment(R.id.bottom_navigation_friends);
        }

        Intent intent = new Intent();
        intent.setClass(this, FirebaseService.class);
        this.startService(intent);
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
            R.navigation.nav_graph_friends,
            R.navigation.nav_graph_analytics
        };

        BottomNavigationUtil.setup(
            binding.bottomNavigation,
            getSupportFragmentManager(),
            navResourceIds,
            R.id.nav_host_conatiner
        );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}