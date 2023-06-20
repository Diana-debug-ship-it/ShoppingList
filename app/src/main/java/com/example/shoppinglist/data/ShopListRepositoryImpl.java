package com.example.shoppinglist.data;

import static com.example.shoppinglist.Constants.UNDEFINED_ID;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppinglist.domain.ShopItem;
import com.example.shoppinglist.domain.ShopListRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import kotlin.random.Random;

public class ShopListRepositoryImpl implements ShopListRepository {

    private static ShopListRepositoryImpl instance = null;

    public static ShopListRepositoryImpl getInstance() {
        if (instance==null) {
            return new ShopListRepositoryImpl();
        }
        return instance;
    }

    private Set<ShopItem> shopList = new TreeSet<>((Comparator.comparing(ShopItem::getId)));

    private MutableLiveData<List<ShopItem>> shopListLD = new MutableLiveData<>();

    private int autoIncrementId = 0;

    {
        for (int i=0; i<10; i++) {
            ShopItem item = new ShopItem("Name"+i, i, Random.Default.nextBoolean());
            addShopItem(item);
        }
    }

    @Override
    public void addShopItem(ShopItem shopItem) {
        if (shopItem.getId().equals(UNDEFINED_ID)) {
            shopItem.setId(autoIncrementId++);
        }
        shopList.add(shopItem);
        updateList();
    }

    @Override
    public void deleteShopItem(ShopItem shopItem) {
        shopList.remove(shopItem);
        updateList();
    }

    @Override
    public void editShopItem(ShopItem shopItem) {
        ShopItem oldItem = getShopItem(shopItem.getId());
        shopList.remove(oldItem);
        addShopItem(shopItem);
    }

    @Override
    public ShopItem getShopItem(Integer id) {
        Optional<ShopItem> temp = shopList.stream().filter(shopItem -> shopItem.getId().equals(id))
                .findFirst();
        if (temp.isPresent()) return temp.get();
        else throw new RuntimeException(String.format("Element with %d not found", id));
    }

    @Override
    public LiveData<List<ShopItem>> getShopList() {
        return shopListLD;
    }

    private void updateList(){
        shopListLD.setValue(new ArrayList<>(shopList));
    }
}
