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

public class SubcategoryFragment extends Fragment {

    private FragmentSubcategoryBinding binding;
    private MainActivity mainActivity;
    private NavController navController;
    private SubcategoryViewModel subcategoryViewModel;

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

        long idCat = SubcategoryFragmentArgs.fromBundle(requireArguments()).getCategoryId();
        subcategoryViewModel.setCurrentCategoryId(idCat);

        SubcategoryAdapter subcategoryAdapter = new SubcategoryAdapter(subcategoryViewModel);
        subcategoryViewModel.getSubcategories().observe(getViewLifecycleOwner(), subcategoryAdapter::setSubcategories);

        binding.recyclerViewSub.setAdapter(subcategoryAdapter);
        binding.recyclerViewSub.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.addSubcategory.setOnClickListener(view -> {
            final EditText input = new EditText(mainActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
            input.setLayoutParams(lp);

            new MaterialAlertDialogBuilder(mainActivity)
                    .setTitle("Insert name of your subcategory:")
                    .setView(input)
                    .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
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
    }
}