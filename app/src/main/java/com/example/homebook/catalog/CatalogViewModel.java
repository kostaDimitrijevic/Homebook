package com.example.homebook.catalog;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogdata.CatalogRepository;
import com.example.homebook.data.catalogitemsdata.CatalogItems;
import com.example.homebook.data.catalogitemsdata.CatalogItemsRepository;
import com.example.homebook.data.itemsdata.Item;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CatalogViewModel extends ViewModel {

    private final SavedStateHandle savedStateHandle;
    private final CatalogRepository catalogRepository;
    private final CatalogItemsRepository catalogItemsRepository;
    private LiveData<List<Catalog>> catalogList;
    private LiveData<List<Long>> catalogItemIdsList;
    private long currentCatalog;
    private static final String CURRENT_CATALOG = "current-catalog";
    private String catalogName;

    @Inject
    public CatalogViewModel(SavedStateHandle savedStateHandle, CatalogRepository catalogRepository, CatalogItemsRepository catalogItemsRepository) {
        this.savedStateHandle = savedStateHandle;
        this.catalogRepository = catalogRepository;
        this.catalogItemsRepository = catalogItemsRepository;

        catalogList = this.catalogRepository.getAllCatalogs();
        catalogItemIdsList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_CATALOG, 0),
                (Function<Integer, LiveData<List<Long>>>) this.catalogItemsRepository::retrieveItemIdsByCatalog);
    }

    public long insertCatalog(Catalog catalog){
        return this.catalogRepository.insert(catalog);
    }

    public LiveData<List<Catalog>> getAllCatalogs(){
        return catalogList;
    }

    public void insertItemForCatalog(long idC, Item item){
        this.catalogItemsRepository.insert(idC, item.getId(), item.getAmount());
    }

    public LiveData<List<Long>> retrieveItemIdsByCatalog(){
        return catalogItemIdsList;
    }

    public void setCurrentCatalog(long currentCatalog) {
        this.currentCatalog = currentCatalog;
        this.savedStateHandle.set(CURRENT_CATALOG, currentCatalog);
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
}

