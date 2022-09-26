package com.example.homebook.analytics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.homebook.data.analyticsdata.AnalyticsItem;
import com.example.homebook.data.analyticsdata.AnalyticsItemRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AnalyticsViewModel extends ViewModel {

    private SavedStateHandle savedStateHandle;
    private AnalyticsItemRepository analyticsItemRepository;
    private LiveData<List<AnalyticsItem>> analyticsItemList;

    @Inject
    public AnalyticsViewModel(SavedStateHandle savedStateHandle, AnalyticsItemRepository analyticsItemRepository) {

        this.savedStateHandle = savedStateHandle;
        this.analyticsItemRepository = analyticsItemRepository;

        analyticsItemList = this.analyticsItemRepository.getTop5MaxAmountItems();
    }

    public LiveData<List<AnalyticsItem>> getAnalyticsItemList() {
        return analyticsItemList;
    }

    public long insert(String itemName, int amountBought){
        return analyticsItemRepository.insert(itemName, amountBought);
    }

    public void update(int amountBought, long id){
        analyticsItemRepository.update(amountBought, id);
    }

    public AnalyticsItem getItemByName(String itemName){
        return analyticsItemRepository.getItemByName(itemName);
    }
}
