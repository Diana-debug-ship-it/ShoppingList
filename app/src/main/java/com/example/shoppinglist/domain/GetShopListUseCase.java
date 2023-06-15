package com.example.shoppinglist.domain;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GetShopListUseCase {

    private ShopListRepository shopListRepository;

    public GetShopListUseCase(ShopListRepository shopListRepository) {
        this.shopListRepository = shopListRepository;
    }

    public LiveData<List<ShopItem>> getShopList(){
        return shopListRepository.getShopList();
    }
}
