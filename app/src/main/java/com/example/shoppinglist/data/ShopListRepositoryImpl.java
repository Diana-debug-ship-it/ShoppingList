package com.example.shoppinglist.data;

import static com.example.shoppinglist.Constants.UNDEFINED_ID;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

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

    private static ShopListDao shopListDao;
    private ShopListMapper mapper = new ShopListMapper();
    private static ShopListRepositoryImpl instance = null;

    public static ShopListRepositoryImpl getInstance(Application application) {
        shopListDao = AppDatabase.getInstance(application).shopListDao();
        if (instance==null) {
            return new ShopListRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void addShopItem(ShopItem shopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem));
    }

    @Override
    public void deleteShopItem(ShopItem shopItem) {
        shopListDao.deleteShopItem(shopItem.getId());
    }

    @Override
    public void editShopItem(ShopItem shopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem));
    }

    @Override
    public ShopItem getShopItem(Integer id) {
        ShopItemDbModel item = shopListDao.getShopItem(id);
        return mapper.mapDbModelToEntity(item);
    }

    @Override
    public LiveData<List<ShopItem>> getShopList() {
//        MediatorLiveData<List<ShopItem>> mediatorLiveData = new MediatorLiveData<>();
//        mediatorLiveData.addSource(shopListDao.getShopList(),
//                value -> mapper.mapListDbModelToListEntity(value));
//        return mediatorLiveData;
        return Transformations.map(shopListDao.getShopList(),
                value -> mapper.mapListDbModelToListEntity(value));
    }
}
