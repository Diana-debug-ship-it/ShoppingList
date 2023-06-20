package com.example.shoppinglist.presentation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;

public class ShopItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tvCount;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvCount() {
        return tvCount;
    }

    public ShopItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.textViewName);
        tvCount = itemView.findViewById(R.id.textViewCount);
    }
}
