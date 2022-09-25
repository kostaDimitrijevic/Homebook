package com.example.homebook.item;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.data.itemsdata.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ItemViewModel extends ViewModel {

    private final LiveData<List<Item>> itemList;
    private final LiveData<List<Item>> subItemList;
    private final LiveData<List<Item>> itemsZero;
    private final LiveData<List<Item>> itemsNotZero;
    private List<Item> itemsToAdd = new ArrayList<>();

    private final SavedStateHandle savedStateHandle;
    private final ItemRepository itemRepository;

    private long currentCategoryId;
    private long currentSubcategoryId;
    private boolean forCategory = true;

    private static final String CURRENT_CATEGORY ="current-category";
    private static final String CURRENT_SUBCATEGORY = "current-subcategory";

    @Inject
    public ItemViewModel(@NonNull SavedStateHandle savedStateHandle, ItemRepository itemRepository) {

        this.savedStateHandle = savedStateHandle;
        this.itemRepository = itemRepository;

            itemList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_CATEGORY, 0L),
                    (Function<Long, LiveData<List<Item>>>) this.itemRepository::getAllItemsByCategoryId);

            subItemList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_SUBCATEGORY, 0L),
                    (Function<Long, LiveData<List<Item>>>) this.itemRepository::getAllItemsBySubcategoryId);

            itemsZero = this.itemRepository.getAllItemsWithAmountZero();

            itemsNotZero = this.itemRepository.getAllItemsWithAmountNotZero();
    }

    public LiveData<List<Item>> getItemList() {
        return itemList;
    }

    public LiveData<List<Item>> getAllItemsWithAmountZero(){
        return itemsZero;
    }

    public LiveData<List<Item>> getAllItemsWithAmountNotZero() {
        return itemsNotZero;
    }

    public List<Item> getItemsToAdd() {
        return itemsToAdd;
    }

    public LiveData<List<Item>> getSubItemList() {
        return subItemList;
    }

    public long getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(long currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
        savedStateHandle.set(CURRENT_CATEGORY, currentCategoryId);
    }

    public long insertItem(Item item){
        return itemRepository.insert(item.getItemName(), item.getIdC(), item.getIdS(), item.getAmount());
    }

    public void deleteItem(long id){
        itemRepository.delete(id);
    }

    public Item getItemById(long id){
        return itemRepository.getItemById(id);
    }

    public void updateAmount(long id, int amount){
        itemRepository.updateAmount(id, amount);
    }

    public boolean isForCategory() {
        return forCategory;
    }

    public void setForCategory(boolean forCategory) {
        this.forCategory = forCategory;
    }

    public long getCurrentSubcategoryId() {
        return currentSubcategoryId;
    }

    public void setCurrentSubcategoryId(long currentSubcategoryId) {
        this.currentSubcategoryId = currentSubcategoryId;
        this.savedStateHandle.set(CURRENT_SUBCATEGORY, currentSubcategoryId);
    }

}
