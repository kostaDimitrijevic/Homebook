package com.example.homebook.catalog;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.JoinItemsCatalog;
import com.example.homebook.data.catalogdata.Catalog;
import com.example.homebook.data.catalogdata.CatalogRepository;
import com.example.homebook.data.catalogitemsdata.CatalogItemsRepository;
import com.example.homebook.data.itemsdata.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CatalogViewModel extends ViewModel {

    private final SavedStateHandle savedStateHandle;
    private final CatalogRepository catalogRepository;
    private final CatalogItemsRepository catalogItemsRepository;
    private LiveData<List<Catalog>> catalogList;
    private LiveData<List<JoinItemsCatalog>> joinItemsCatalogList;
    private List<JoinItemsCatalog> firebaseItemCatalogList = new ArrayList<>();
    private long currentCatalog;
    private static final String CURRENT_CATALOG = "current-catalog";
    private String catalogName;
    private boolean showCatalog;

    @Inject
    public CatalogViewModel(SavedStateHandle savedStateHandle, CatalogRepository catalogRepository, CatalogItemsRepository catalogItemsRepository) {
        this.savedStateHandle = savedStateHandle;
        this.catalogRepository = catalogRepository;
        this.catalogItemsRepository = catalogItemsRepository;

        catalogList = this.catalogRepository.getAllCatalogs();
        joinItemsCatalogList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_CATALOG, 0L),
                (Function<Long, LiveData<List<JoinItemsCatalog>>>) this.catalogItemsRepository::getItemsForCatalog);
    }

    public long insertCatalog(Catalog catalog){
        return this.catalogRepository.insert(catalog);
    }

    public void deleteCatalog(long idC) {
        this.catalogRepository.delete(idC);
    }

    public void deleteCatalogItems(long idC) {
        this.catalogItemsRepository.deleteCatalogItems(idC);
    }

    public LiveData<List<Catalog>> getAllCatalogs(){
        return catalogList;
    }

    public void insertItemForCatalog(long idC, Item item){
        this.catalogItemsRepository.insert(idC, item.getId(), item.getAmount());
    }

    public LiveData<List<JoinItemsCatalog>> getItemsForCatalog(){
        return joinItemsCatalogList;
    }

    public void setCurrentCatalog(Long currentCatalog) {
        this.currentCatalog = currentCatalog;
        this.savedStateHandle.set(CURRENT_CATALOG, currentCatalog);
    }

    public long getCurrentCatalog() {
        return currentCatalog;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public boolean isShowCatalog() {
        return showCatalog;
    }

    public void setShowCatalog(boolean showCatalog) {
        this.showCatalog = showCatalog;
    }

    public List<JoinItemsCatalog> getFirebaseItemCatalogList() {
        return firebaseItemCatalogList;
    }

    public void setFirebaseItemCatalogList(List<JoinItemsCatalog> firebaseItemCatalogList) {
        this.firebaseItemCatalogList = firebaseItemCatalogList;
    }
}

