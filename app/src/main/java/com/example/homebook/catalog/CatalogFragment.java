package com.example.homebook.catalog;

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
import com.example.homebook.data.JoinItemsCatalog;
import com.example.homebook.data.firebase.Catalog;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.FragmentCatalogBinding;
import com.example.homebook.item.ItemViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CatalogFragment extends Fragment {

    private FragmentCatalogBinding binding;
    private MainActivity mainActivity;
    private CatalogViewModel catalogViewModel;
    private ItemViewModel itemViewModel;
    private NavController navController;

    public CatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        catalogViewModel = new ViewModelProvider(mainActivity).get(CatalogViewModel.class);
        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCatalogBinding.inflate(inflater, container, false);

        if(MainActivity.firebaseCatalogList.size() > 0){
            for (int i = MainActivity.readFirebaseCatalogs; i < MainActivity.firebaseCatalogList.size(); i = ++MainActivity.readFirebaseCatalogs) {
                Catalog catalog = MainActivity.firebaseCatalogList.get(i);
                long id = catalogViewModel.insertCatalog(new com.example.homebook.data.catalogdata.Catalog(0, catalog.getCatalogName(), 0, catalog.getUserEmail(), catalog.getDate()));
                for (JoinItemsCatalog joinItem : catalog.getCatalogItems()) {
                    long idI = itemViewModel.insertItem(new Item(0, joinItem.getItemName(), id, null, joinItem.getAmount()));
                    Item item = itemViewModel.getItemById(idI);
                    catalogViewModel.insertItemForCatalog(id, item);
                }
            }
        }

        CatalogAdapter catalogAdapter = new CatalogAdapter(catalogViewModel, (name, user) ->{
            CatalogFragmentDirections.ActionToCatalogItems action = CatalogFragmentDirections.actionToCatalogItems();
            action.setUserCatalog(user);
            action.setShowCatalog(true);
            action.setCatalogName(name);
            navController.navigate(action);
        });
        catalogViewModel.getAllCatalogs().observe(getViewLifecycleOwner(), catalogAdapter::setCatalogs);

        binding.catalogRecyclerView.setAdapter(catalogAdapter);
        binding.catalogRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.addCatalogBtn.setOnClickListener(view -> {
            final EditText input = new EditText(mainActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 100);
            input.setLayoutParams(lp);

            new MaterialAlertDialogBuilder(mainActivity)
                    .setTitle("Insert name of your shopping list:")
                    .setView(input)
                    .setNeutralButton(R.string.neutral_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setNegativeButton(R.string.decline_btn, (dialog, which) -> {
                        // nothing happens
                    })
                    .setPositiveButton(R.string.accept_btn, (dialog, which) -> {
                        String newCatalog = input.getText().toString();
                        CatalogFragmentDirections.ActionToCatalogItems action = CatalogFragmentDirections.actionToCatalogItems();
                        action.setShowCatalog(false);
                        action.setCatalogName(newCatalog);
                        navController.navigate(action);
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