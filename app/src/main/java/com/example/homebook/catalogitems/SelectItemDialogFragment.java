package com.example.homebook.catalogitems;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.homebook.MainActivity;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.FragmentSelectItemDialogBinding;
import com.example.homebook.item.ItemViewModel;

import java.util.List;

public class SelectItemDialogFragment extends DialogFragment {

    public interface Callback<T>{
        void Invoke(T parameter);
    }

    private FragmentSelectItemDialogBinding binding;
    private MainActivity mainActivity;
    private ItemViewModel itemViewModel;
    private Callback<List<Item>> callback;

    public SelectItemDialogFragment(Callback<List<Item>> callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectItemDialogBinding.inflate(inflater, container, false);

        SelectItemDialogAdapter selectItemDialogAdapter = new SelectItemDialogAdapter(itemViewModel);
        itemViewModel.getAllItemsWithAmountNotZero().observe(getViewLifecycleOwner(), selectItemDialogAdapter::setItemList);

        binding.selectItemRecyclerView.setAdapter(selectItemDialogAdapter);
        binding.selectItemRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.selectItemsButton.setOnClickListener(view -> {
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        this.callback.Invoke(itemViewModel.getItemsToAdd());
    }
}
