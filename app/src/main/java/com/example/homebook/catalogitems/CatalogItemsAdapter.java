package com.example.homebook.catalogitems;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatalogItemsAdapter extends RecyclerView.Adapter<CatalogItemsAdapter.ListViewHolder> {
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
