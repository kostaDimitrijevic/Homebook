package com.example.homebook.item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.R;
import com.example.homebook.databinding.FragmentItemCreateBinding;

public class ItemCreateFragment extends Fragment {

    FragmentItemCreateBinding binding;

    public ItemCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemCreateBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}