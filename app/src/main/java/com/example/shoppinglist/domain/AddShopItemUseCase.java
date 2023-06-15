package com.example.shoppinglist.domain;

public class AddShopItemUseCase{

    private ShopListRepository shopListRepository;

    public AddShopItemUseCase(ShopListRepository shopListRepository) {
        this.shopListRepository = shopListRepository;
    }

    public void addShopItem(ShopItem shopItem) {
        shopListRepository.addShopItem(shopItem);
    }
}
