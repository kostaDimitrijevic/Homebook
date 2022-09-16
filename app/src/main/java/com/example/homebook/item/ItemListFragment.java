package com.example.homebook.item;

import android.graphics.Color;
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

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.databinding.FragmentItemListBinding;

public class ItemListFragment extends Fragment {

    private FragmentItemListBinding binding;
    private ItemViewModel itemViewModel;
    private MainActivity mainActivity;
    private NavController navController;

    public ItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        itemViewModel.setCurrentCategoryId(ItemListFragmentArgs.fromBundle(requireArguments()).getCategoryId());

        ItemListAdapter itemListAdapter = new ItemListAdapter();
        itemViewModel.getItemList().observe(getViewLifecycleOwner(), itemListAdapter::setItemList);

        binding.toolbar.setTitle(ItemListFragmentArgs.fromBundle(requireArguments()).getCategoryName());
        binding.recyclerView.setAdapter(itemListAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.addItem.setOnClickListener(view -> {
            ItemListFragmentDirections.ActionCreate action = ItemListFragmentDirections.actionCreate();
            action.setCategoryId(itemViewModel.getCurrentCategoryId());
            navController.navigate(action);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}