package com.example.shoppinglist.domain;

public class EditShopItemUseCase {

    private ShopListRepository shopListRepository;

    public EditShopItemUseCase(ShopListRepository shopListRepository) {
        this.shopListRepository = shopListRepository;
    }

    public void editShopItem(ShopItem shopItem) {
        shopListRepository.editShopItem(shopItem);
    }
}
