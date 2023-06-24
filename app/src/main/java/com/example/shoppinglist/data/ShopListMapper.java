package com.example.shoppinglist.data;

import static com.example.shoppinglist.Constants.UNDEFINED_ID;

import com.example.shoppinglist.domain.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ShopListMapper {
    public ShopItemDbModel mapEntityToDbModel(ShopItem shopItem){
        ShopItemDbModel item =  new ShopItemDbModel(
                shopItem.getName(),
                shopItem.getCount(),
                shopItem.isEnabled());
        if (shopItem.getId() != UNDEFINED_ID) {
            item.setId(shopItem.getId());
        }
        return item;
    }

    public ShopItem mapDbModelToEntity(ShopItemDbModel item){
        ShopItem shopItem = new ShopItem(
                item.getName(),
                item.getCount(),
                item.isEnabled());
        shopItem.setId(item.getId());
        return shopItem;
    }

    public List<ShopItem> mapListDbModelToListEntity(List<ShopItemDbModel> list) {
        List<ShopItem> newList = new ArrayList<>();
        for (ShopItemDbModel item: list) {
            newList.add(mapDbModelToEntity(item));
        }
        return newList;
    }
}
