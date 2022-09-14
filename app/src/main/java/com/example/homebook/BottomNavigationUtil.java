package com.example.homebook;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.example.homebook.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class BottomNavigationUtil {

    private interface NavHostFragmentChanger{
        NavController change(int id);
    }

    private static NavHostFragmentChanger navHostFragmentChanger;

    public static void setup(
            BottomNavigationView bottomNavigationView,
            FragmentManager fragmentManager,
            int[] navResourceIds,
            int containerId
    ){
        int homeNavGraphId = 0;
        Map<Integer, String> navGraphIdToTagMap = new HashMap<>();
        for (int i = 0; i < navResourceIds.length; i++){
            String tag = "navHostFragment#" + i;
            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, tag, navResourceIds[i], containerId);

            int navGraphId = navHostFragment.getNavController().getGraph().getId();

            navGraphIdToTagMap.put(navGraphId, tag);
            if(i == 0){
                homeNavGraphId = navGraphId;
            }

            if(navGraphId == R.id.nav_graph_login){
                attachNavHostFragment(fragmentManager, navHostFragment, i == 0);
            }
            else{
                if(bottomNavigationView.getSelectedItemId() == navGraphId && bottomNavigationView.getVisibility() == View.VISIBLE){
                    attachNavHostFragment(fragmentManager, navHostFragment, i == 0);
                }
                else{
                    detachNavHostFragment(fragmentManager, navHostFragment);
                }
            }

        }

        String homeTag = navGraphIdToTagMap.get(homeNavGraphId);
        AtomicReference<String> currentTagWrapper;
        if(bottomNavigationView.getVisibility() != View.VISIBLE){
            currentTagWrapper = new AtomicReference<>(homeTag);
        }
        else{
            currentTagWrapper  = new AtomicReference<>(navGraphIdToTagMap.get(bottomNavigationView.getSelectedItemId()));
        }

        AtomicReference<Boolean> isOnHomeWrapper = new AtomicReference<>(
                homeNavGraphId == bottomNavigationView.getSelectedItemId()
        );

        navHostFragmentChanger = id -> {

            if(!fragmentManager.isStateSaved()){
                String dstTag = navGraphIdToTagMap.get(id);
                NavHostFragment homeNavHostFragment = (NavHostFragment) fragmentManager.findFragmentByTag(homeTag);
                NavHostFragment dstNavHostFragment = (NavHostFragment) fragmentManager.findFragmentByTag(dstTag);

                bottomNavigationView.getMenu().findItem(id).setChecked(true);

                if(!dstTag.equals(currentTagWrapper.get())){
                    fragmentManager.popBackStack(homeTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    if(!dstTag.equals(homeTag)){
                        fragmentManager
                                .beginTransaction()
                                .detach(homeNavHostFragment)
                                .attach(dstNavHostFragment)
                                .setPrimaryNavigationFragment(dstNavHostFragment)
                                .addToBackStack(homeTag)
                                .setReorderingAllowed(true)
                                .commit();
                    }

                    currentTagWrapper.set(dstTag);
                    isOnHomeWrapper.set(dstTag.equals(homeTag));

                    if(bottomNavigationView.getVisibility() != View.VISIBLE){
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }

                }
                return dstNavHostFragment.getNavController();
            }

            return null;
        };

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            return navHostFragmentChanger.change(menuItem.getItemId()) != null;
        });

        int finalHomeNavGraphId = homeNavGraphId;
        fragmentManager.addOnBackStackChangedListener(() -> {
            if(!isOnHomeWrapper.get() && !isOnBackStack(fragmentManager, homeTag)){
                bottomNavigationView.setSelectedItemId(finalHomeNavGraphId);
            }
        });
    }

    private static NavHostFragment obtainNavHostFragment(
            FragmentManager fragmentManager,
            String tag,
            int navResourceId,
            int containerId
    ){
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentByTag(tag);

        if(navHostFragment != null){
            return navHostFragment;
        }

        NavHostFragment newNavHostFragment = NavHostFragment.create(navResourceId);
        fragmentManager
                .beginTransaction()
                .add(containerId, newNavHostFragment, tag)
                .commitNow();

        return newNavHostFragment;
    }

    private static void attachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment, boolean isPrimary){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.attach(navHostFragment);

        if(isPrimary){
            fragmentTransaction.setPrimaryNavigationFragment(navHostFragment);
        }

        fragmentTransaction.commitNow();
    }

    private static void detachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment){
        fragmentManager
                .beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }

    private static  boolean isOnBackStack(FragmentManager fragmentManager, String backStackEntryName){
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++){
            if(fragmentManager.getBackStackEntryAt(i).getName().equals(backStackEntryName)){
                return true;
            }
        }

        return false;
    }

    private static void resetLogin(FragmentManager fragmentManager, String loginTag){
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++){
            if(fragmentManager.getBackStackEntryAt(i).getName().equals(loginTag)){

            }
        }
    }

    public static NavController changeNavHostFragment(int id){
        return navHostFragmentChanger.change(id);
    }
}
