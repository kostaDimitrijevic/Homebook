package com.example.homebook.catalogitems;

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

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.catalog.CatalogViewModel;
import com.example.homebook.databinding.FragmentCatalogItemsBinding;
import com.example.homebook.item.ItemViewModel;

public class CatalogItemsFragment extends Fragment {

    private FragmentCatalogItemsBinding binding;
    private CatalogViewModel catalogViewModel;
    private ItemViewModel itemViewModel;
    private NavController navController;
    private MainActivity mainActivity;

    public CatalogItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
        catalogViewModel = new ViewModelProvider(mainActivity).get(CatalogViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatalogItemsBinding.inflate(inflater, container, false);
        binding.toolbarCatalogItems.setTitle(CatalogItemsFragmentArgs.fromBundle(requireArguments()).getCatalogName());
        catalogViewModel.setCatalogName(CatalogItemsFragmentArgs.fromBundle(requireArguments()).getCatalogName());

        CatalogItemsAdapter catalogItemsAdapter = new CatalogItemsAdapter(catalogViewModel);
        itemViewModel.getAllItemsWithAmountZero().observe(getViewLifecycleOwner(), catalogItemsAdapter::setCatalogItemsList);
        binding.recyclerViewCatalogItems.setAdapter(catalogItemsAdapter);
        binding.recyclerViewCatalogItems.setLayoutManager(new LinearLayoutManager(mainActivity));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}