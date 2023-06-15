package com.example.shoppinglist.domain;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ShopListRepository {

    public void addShopItem(ShopItem shopItem);

    public void deleteShopItem(ShopItem shopItem);

    public void editShopItem(ShopItem shopItem);

    public ShopItem getShopItem(int id);

    public LiveData<List<ShopItem>> getShopList();
}
