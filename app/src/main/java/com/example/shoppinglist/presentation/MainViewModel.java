package com.example.shoppinglist.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.data.ShopListRepositoryImpl;
import com.example.shoppinglist.domain.DeleteItemUseCase;
import com.example.shoppinglist.domain.EditShopItemUseCase;
import com.example.shoppinglist.domain.GetShopListUseCase;
import com.example.shoppinglist.domain.ShopItem;

import java.util.List;

public class MainViewModel extends ViewModel {

    private ShopListRepositoryImpl repository = ShopListRepositoryImpl.getInstance();

    private GetShopListUseCase getShopListUseCase = new GetShopListUseCase(repository);
    private DeleteItemUseCase deleteItemUseCase = new DeleteItemUseCase(repository);
    private EditShopItemUseCase editShopItemUseCase = new EditShopItemUseCase(repository);

    private LiveData<List<ShopItem>> shopList = getShopListUseCase.getShopList();

    public LiveData<List<ShopItem>> getShopList() {
        return shopList;
    }

    public void deleteShopItem(ShopItem shopItem) {
        deleteItemUseCase.deleteShopItem(shopItem);
    }

    public void changeEnableState(ShopItem shopItem) {
        ShopItem newItem = new ShopItem(shopItem.getName(), shopItem.getCount(), !shopItem.isEnabled());
        newItem.setId(shopItem.getId());
        editShopItemUseCase.editShopItem(newItem);
    }
}
