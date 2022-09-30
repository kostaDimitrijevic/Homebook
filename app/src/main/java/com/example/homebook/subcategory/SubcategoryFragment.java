package com.example.homebook.subcategory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.subcategorydata.Subcategory;
import com.example.homebook.databinding.FragmentSubcategoryBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SubcategoryFragment extends Fragment {

    private FragmentSubcategoryBinding binding;
    private MainActivity mainActivity;
    private NavController navController;
    private SubcategoryViewModel subcategoryViewModel;
    private long idCat;
    private boolean clickedAddSub = false;

    public SubcategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        subcategoryViewModel = new ViewModelProvider(mainActivity).get(SubcategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSubcategoryBinding.inflate(inflater, container, false);
        binding.toolbarSub.setTitle(SubcategoryFragmentArgs.fromBundle(requireArguments()).getCategoryName() + "  -> SUBCATEGORIES:");

        idCat = SubcategoryFragmentArgs.fromBundle(requireArguments()).getCategoryId();
        subcategoryViewModel.setCurrentCategoryId(idCat);

        SubcategoryAdapter subcategoryAdapter = new SubcategoryAdapter(subcategoryViewModel, subId -> {
            SubcategoryFragmentDirections.ActionSubItem action = SubcategoryFragmentDirections.actionSubItem();
            action.setCategoryId(idCat);
            action.setSubcategoryId(subId);
            action.setCategoryName(subcategoryViewModel.getSubcategoryNameById(subId));
            action.setIsCategory(false);
            navController.navigate(action);
        });
        subcategoryViewModel.getSubcategories().observe(getViewLifecycleOwner(), subcategoryAdapter::setSubcategories);

        binding.recyclerViewSub.setAdapter(subcategoryAdapter);
        binding.recyclerViewSub.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.addSubcategory.setOnClickListener(view -> {
            clickedAddSub = true;
            final EditText input = new EditText(mainActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
            input.setLayoutParams(lp);

            new MaterialAlertDialogBuilder(mainActivity)
                    .setTitle("Insert name of your subcategory:")
                    .setView(input)
                    .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                        // nothing happens
                        clickedAddSub = false;
                    })
                    .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                        // nothing happens
                        clickedAddSub = false;
                    })
                    .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                        clickedAddSub = false;
                        String newSubcategory = input.getText().toString();
                        subcategoryViewModel.insertSubcategory(new Subcategory(0, idCat, newSubcategory));
                        subcategoryViewModel.setCurrentCategoryId(idCat);
                    })
                    .show();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("clicked_add_sub")){
                binding.addSubcategory.callOnClick();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(binding != null){
            if(clickedAddSub){
                outState.putBoolean("clicked_add_sub", true);
                clickedAddSub = false;
            }
            else{
                outState.putBoolean("clicked_add_sub", false);
            }
        }
    }
}