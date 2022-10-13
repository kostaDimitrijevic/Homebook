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
import android.widget.Toast;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.FragmentItemCreateBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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
            if(!binding.itemName.getEditText().getText().toString().equals("") && !binding.itemAmount.getEditText().getText().toString().equals("")){
                String itemName = binding.itemName.getEditText().getText().toString();
                int amount = Integer.parseInt(binding.itemAmount.getEditText().getText().toString());
                long categoryId = ItemCreateFragmentArgs.fromBundle(requireArguments()).getCategoryId();
                long subcategoryId = ItemCreateFragmentArgs.fromBundle(requireArguments()).getSubcategoryId();
                if(subcategoryId == -1){
                    itemViewModel.insertItem(new Item(0, itemName, categoryId, null, amount));
                }
                else{
                    itemViewModel.insertItem(new Item(0, itemName, categoryId, subcategoryId, amount));
                }
                navController.navigateUp();
            }
            else{
                Toast.makeText(mainActivity, "You need to fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}