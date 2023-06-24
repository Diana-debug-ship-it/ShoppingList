package com.example.shoppinglist.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.data.ShopListRepositoryImpl;
import com.example.shoppinglist.domain.DeleteItemUseCase;
import com.example.shoppinglist.domain.EditShopItemUseCase;
import com.example.shoppinglist.domain.GetShopListUseCase;
import com.example.shoppinglist.domain.ShopItem;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private ShopListRepositoryImpl repository = ShopListRepositoryImpl.getInstance(getApplication());

    private GetShopListUseCase getShopListUseCase = new GetShopListUseCase(repository);
    private DeleteItemUseCase deleteItemUseCase = new DeleteItemUseCase(repository);
    private EditShopItemUseCase editShopItemUseCase = new EditShopItemUseCase(repository);

    private LiveData<List<ShopItem>> shopList = getShopListUseCase.getShopList();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

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
