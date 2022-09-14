package com.example.homebook.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.R;
import com.example.homebook.databinding.ActivityMainBinding;
import com.example.homebook.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentCategoryBinding.inflate(inflater, container, false);

       return binding.getRoot();
    }
}