package com.example.homebook.item;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.itemsdata.Item;
import com.example.homebook.data.itemsdata.ItemRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ItemViewModel extends ViewModel {

    private final LiveData<List<Item>> itemList;
    private final SavedStateHandle savedStateHandle;
    private final ItemRepository itemRepository;
    private long currentCategoryId;

    private static final String CURRENT_CATEGORY ="current-category";

    @Inject
    public ItemViewModel(@NonNull SavedStateHandle savedStateHandle, ItemRepository itemRepository) {

        this.savedStateHandle = savedStateHandle;
        this.itemRepository = itemRepository;

        itemList = Transformations.switchMap(this.savedStateHandle.getLiveData(CURRENT_CATEGORY, 0L),
                (Function<Long, LiveData<List<Item>>>) this.itemRepository::getAllItemsByCategoryId);
    }

    public LiveData<List<Item>> getItemList() {
        return itemList;
    }

    public long getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(long currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
        savedStateHandle.set(CURRENT_CATEGORY, currentCategoryId);
    }

    public void insertItem(Item item){
        itemRepository.insert(item.getItemName(), item.getIdC(), item.getAmount());
    }

    public void deleteItem(long id){
        itemRepository.delete(id);
    }
}
