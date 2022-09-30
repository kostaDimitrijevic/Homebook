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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homebook.MainActivity;
import com.example.homebook.R;
import com.example.homebook.analytics.AnalyticsViewModel;
import com.example.homebook.catalog.CatalogViewModel;
import com.example.homebook.data.JoinItemsCatalog;
import com.example.homebook.data.analyticsdata.AnalyticsItem;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogitemsdata.CatalogItems;
import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.databinding.FragmentCatalogItemsBinding;
import com.example.homebook.item.ItemViewModel;
import com.example.homebook.services.DateTimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogItemsFragment extends Fragment {

    private FragmentCatalogItemsBinding binding;

    private CatalogViewModel catalogViewModel;
    private ItemViewModel itemViewModel;
    private AnalyticsViewModel analyticsViewModel;

    private NavController navController;
    private MainActivity mainActivity;
    private SelectItemDialogFragment selectItemDialogFragment;
    private boolean submitClicked;
    private  boolean addMoreItemsClicked = false;

    public CatalogItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();

        itemViewModel = new ViewModelProvider(mainActivity).get(ItemViewModel.class);
        catalogViewModel = new ViewModelProvider(mainActivity).get(CatalogViewModel.class);
        analyticsViewModel = new ViewModelProvider(mainActivity).get(AnalyticsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatalogItemsBinding.inflate(inflater, container, false);

        submitClicked = false;

        boolean showCatalog = CatalogItemsFragmentArgs.fromBundle(requireArguments()).getShowCatalog();
        String userCatalog = CatalogItemsFragmentArgs.fromBundle(requireArguments()).getUserCatalog();

        catalogViewModel.setShowCatalog(showCatalog);
        binding.toolbarCatalogItems.setTitle(CatalogItemsFragmentArgs.fromBundle(requireArguments()).getCatalogName());
        catalogViewModel.setCatalogName(CatalogItemsFragmentArgs.fromBundle(requireArguments()).getCatalogName());
        CatalogItemsAdapter catalogItemsAdapter = new CatalogItemsAdapter(catalogViewModel);

        if(showCatalog){

            catalogViewModel.getItemsForCatalog().observe(getViewLifecycleOwner(), catalogItemsAdapter::setJoinItemsCatalogList);

            binding.submitList.setVisibility(View.GONE);
            binding.warning.setVisibility(View.GONE);
            binding.buttonAddMoreItems.setVisibility(View.GONE);
            binding.floatingActionButton.setVisibility(View.VISIBLE);
            binding.floatingActionButton.inflate(R.menu.list_options);

            binding.floatingActionButton.setOnActionSelectedListener(actionItem -> {
                switch (actionItem.getId()){
                    case R.id.list_send: {
                        SelectUserDialogFragment selectUserDialogFragment = new SelectUserDialogFragment(this::sendCatalog);
                        selectUserDialogFragment.show(mainActivity.getSupportFragmentManager(), "select-user-dialog");
                        return false;
                    }
                    case R.id.maps:
                        navController.navigate(CatalogItemsFragmentDirections.actionShowMap());
                        return false;
                    case R.id.close:
                        catalogViewModel.getCatalogItems().observe(getViewLifecycleOwner(), catalogItems -> {
                            List<JoinItemsCatalog> joinItemsCatalogs = catalogViewModel.getItemsForCatalog().getValue();
                            if(userCatalog.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                for (CatalogItems i : catalogItems) {
                                    itemViewModel.updateAddToAmount(i.getIdI(), i.getAmount());
                                }
                                for(JoinItemsCatalog joinItem : joinItemsCatalogs){
                                    AnalyticsItem analyticsItem = analyticsViewModel.getItemByName(joinItem.getItemName());
                                    if(analyticsItem != null){
                                        analyticsViewModel.update(analyticsItem.getAmountBought(), analyticsItem.getId());
                                    }
                                    else{
                                        analyticsViewModel.insert(joinItem.getItemName(), joinItem.getAmount());
                                    }
                                }
                                catalogViewModel.updateDate(DateTimeUtil.getSimpleDateFormat().format(new Date()), catalogViewModel.getCurrentCatalog());
                                catalogViewModel.updateStatus(1, catalogViewModel.getCurrentCatalog());
                            }
                            else{
                                for (CatalogItems i : catalogItems) {
                                    itemViewModel.deleteItem(i.getIdI());
                                }
                                catalogViewModel.deleteCatalogItems(catalogViewModel.getCurrentCatalog());
                                catalogViewModel.deleteCatalog(catalogViewModel.getCurrentCatalog());
                            }

                            navController.navigateUp();
                        });

                }

                return true;
            });
        }
        else{
            itemViewModel.getAllItemsWithAmountZero().observe(getViewLifecycleOwner(), catalogItemsAdapter::setCatalogItemsList);
        }

        binding.recyclerViewCatalogItems.setAdapter(catalogItemsAdapter);
        binding.recyclerViewCatalogItems.setLayoutManager(new LinearLayoutManager(mainActivity));

        binding.submitList.setOnClickListener(view -> {

            Date date = new Date();
            String user =  FirebaseAuth.getInstance().getCurrentUser().getEmail();
            long idC = catalogViewModel.insertCatalog(new Catalog(0, catalogViewModel.getCatalogName(), 0, user, DateTimeUtil.getSimpleDateFormat().format(date)));
            List<Item> itemsToInsert = catalogItemsAdapter.getCatalogItemsList();
            for (Item item : itemsToInsert) {
                this.catalogViewModel.insertItemForCatalog(idC, item);
            }
            submitClicked = true;
            navController.navigateUp();
        });



        binding.buttonAddMoreItems.setOnClickListener(view -> {
            addMoreItemsClicked = true;
            selectItemDialogFragment = new SelectItemDialogFragment(itemsToAdd->{
                addMoreItemsClicked = false;
                if(itemsToAdd.size() > 0){
                    catalogItemsAdapter.getCatalogItemsList().addAll(itemsToAdd);
                    catalogItemsAdapter.setCatalogItemsList(catalogItemsAdapter.getCatalogItemsList());
                    itemViewModel.getItemsToAdd().clear();
                }
            });
            selectItemDialogFragment.show(mainActivity.getSupportFragmentManager(), "select-item-dialog");
        });

        getParentFragmentManager().addOnBackStackChangedListener(() -> {
            if(!submitClicked){
                catalogItemsAdapter.getCatalogItemsList().clear();
            }

        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("clicked_add_more")){
                binding.buttonAddMoreItems.callOnClick();
            }
        }
    }

    private void sendCatalog(String toUserEmail){
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String date = DateTimeUtil.getSimpleDateFormat().format(new Date());
        com.example.homebook.data.firebase.Catalog catalog = new com.example.homebook.data.firebase.Catalog(userEmail, toUserEmail, catalogViewModel.getCatalogName(), date, 0, catalogViewModel.getItemsForCatalog().getValue());
        DatabaseReference reference = FirebaseDatabase.getInstance("https://homebook-e8d20-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Catalogs").push();
        Map<String, Object> map = new HashMap<>();
        map.put("catalog", catalog);
        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mainActivity, "Shopping list sent to user:" + toUserEmail, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mainActivity, "Error, SENDING FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(binding != null){
            if(selectItemDialogFragment != null){
                selectItemDialogFragment.dismiss();
                if(selectItemDialogFragment.isDismissedRegular()){
                    addMoreItemsClicked = false;
                    selectItemDialogFragment.setDismissedRegular(false);
                }
                else{
                    addMoreItemsClicked = true;
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(binding != null){
            if(addMoreItemsClicked){
                outState.putBoolean("clicked_add_more", true);
                addMoreItemsClicked = false;
            }
            else{
                outState.putBoolean("clicked_add_more", false);
            }
        }
    }
}