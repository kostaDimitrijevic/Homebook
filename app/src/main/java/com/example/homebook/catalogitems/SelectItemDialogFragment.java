package com.example.homebook.catalogitems;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebook.databinding.FragmentSelectItemDialogBinding;

public class SelectItemDialogFragment extends DialogFragment {

    private FragmentSelectItemDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectItemDialogBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
