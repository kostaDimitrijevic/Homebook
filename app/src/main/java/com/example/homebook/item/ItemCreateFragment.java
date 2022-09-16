package com.example.homebook.item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.FragmentItemCreateBinding;

public class ItemCreateFragment extends Fragment {

    private FragmentItemCreateBinding binding;
    private MainActivity mainActivity;
    private ItemViewModel itemViewModel;
    private NavController navController;

    public ItemCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemCreateBinding.inflate(inflater, container, false);

        binding.buttonCancel.setOnClickListener(view -> {
            navController.navigateUp();
        });

        binding.buttonNewItem.setOnClickListener(view -> {
            String itemName = binding.itemName.getEditText().getText().toString();
            int amount = Integer.parseInt(binding.itemAmount.getEditText().getText().toString());
            long categoryId = ItemCreateFragmentArgs.fromBundle(requireArguments()).getCategoryId();
            itemViewModel.insertItem(new Item(0, itemName, categoryId, amount));
            navController.navigateUp();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}