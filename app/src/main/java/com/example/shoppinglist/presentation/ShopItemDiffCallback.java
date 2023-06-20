package com.example.shoppinglist.presentation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.domain.ShopItem;


//Предпочтительнее использовать лист адаптер и вот это!!!!!!!!!!
public class ShopItemDiffCallback extends DiffUtil.ItemCallback<ShopItem> {

    public ShopItemDiffCallback() {
    }

    @Override
    public boolean areItemsTheSame(@NonNull ShopItem oldItem, @NonNull ShopItem newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ShopItem oldItem, @NonNull ShopItem newItem) {
        return oldItem.equals(newItem);
    }
}
