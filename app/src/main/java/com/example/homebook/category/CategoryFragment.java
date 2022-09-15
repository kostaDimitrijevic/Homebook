package com.example.homebook.category;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.categorydata.Category;
import com.example.homebook.databinding.FragmentCategoryBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryViewModel categoryViewModel;
    private MainActivity mainActivity;
    private NavController navController;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        categoryViewModel = new ViewModelProvider(mainActivity).get(CategoryViewModel.class);

        List<String> imageUrls = new ArrayList<>();
        imageUrls.add(getResources().getText(R.string.Chemistry).toString());
        imageUrls.add(getResources().getText(R.string.Food).toString());
        imageUrls.add(getResources().getText(R.string.Pet).toString());
        imageUrls.add(getResources().getText(R.string.Garden).toString());

        categoryViewModel.setImageUrls(imageUrls);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentCategoryBinding.inflate(inflater, container, false);

       CategoryAdapter categoryAdapter = new CategoryAdapter(categoryViewModel, category -> {
           long categoryId = categoryViewModel.getCategoryIdByName(category);
           CategoryFragmentDirections.ActionItemList action = CategoryFragmentDirections.actionItemList();
           action.setCategoryId(categoryId);
           action.setCategoryName(category);
           navController.navigate(action);
       });

       categoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), categoryAdapter::setCategories);

       binding.recyclerView.setAdapter(categoryAdapter);
       binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

       binding.addCategoryBtn.setOnClickListener(view -> {
           final EditText input = new EditText(mainActivity);
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
           input.setLayoutParams(lp);

           new MaterialAlertDialogBuilder(mainActivity)
                   .setTitle("Insert name of your category:")
                   .setView(input)
                   .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                    // nothing happens
                   })
                   .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                    // nothing happens
                   })
                   .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                       String newCategory = input.getText().toString();
                       categoryViewModel.insertCategory(newCategory);
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