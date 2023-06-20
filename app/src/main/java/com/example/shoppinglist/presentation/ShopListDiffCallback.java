package com.example.shoppinglist.presentation;

import androidx.recyclerview.widget.DiffUtil;

import com.example.shoppinglist.domain.ShopItem;

import java.util.List;
//Этот способ конечно рабочий но можно сделать лучше!!!!!!!!!!!!!!
public class ShopListDiffCallback extends DiffUtil.Callback {

    private List<ShopItem> oldList;
    private List<ShopItem> newList;

    public ShopListDiffCallback(List<ShopItem> oldList, List<ShopItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
