package com.example.shoppinglist.domain;

public class DeleteItemUseCase {

    private ShopListRepository shopListRepository;

    public DeleteItemUseCase(ShopListRepository shopListRepository) {
        this.shopListRepository = shopListRepository;
    }

    public void deleteShopItem(ShopItem shopItem){
        shopListRepository.deleteShopItem(shopItem);
    }
}
