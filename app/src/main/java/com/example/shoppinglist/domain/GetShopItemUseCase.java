package com.example.shoppinglist.domain;

public class GetShopItemUseCase {

    private ShopListRepository shopListRepository;

    public GetShopItemUseCase(ShopListRepository shopListRepository) {
        this.shopListRepository = shopListRepository;
    }

    public ShopItem getShopItem(int id) {
        return shopListRepository.getShopItem(id);
    }
}
