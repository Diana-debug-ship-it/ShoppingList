package com.example.shoppinglist.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppinglist.data.ShopListRepositoryImpl;
import com.example.shoppinglist.domain.AddShopItemUseCase;
import com.example.shoppinglist.domain.EditShopItemUseCase;
import com.example.shoppinglist.domain.GetShopItemUseCase;
import com.example.shoppinglist.domain.ShopItem;

public class ShopItemViewModel extends ViewModel {

    private ShopListRepositoryImpl repository = ShopListRepositoryImpl.getInstance();

    private GetShopItemUseCase getShopItemUseCase = new GetShopItemUseCase(repository);
    private AddShopItemUseCase addShopItemUseCase = new AddShopItemUseCase(repository);
    private EditShopItemUseCase editShopItemUseCase = new EditShopItemUseCase(repository);

    private MutableLiveData<Boolean> errorInputName = new MutableLiveData<>();
    private MutableLiveData<Boolean> errorInputCount = new MutableLiveData<>();
    private MutableLiveData<ShopItem> shopItemLD = new MutableLiveData<>();
    private MutableLiveData<Object> isFinished = new MutableLiveData<>();

    public LiveData<Object> getIsFinished() {
        return isFinished;
    }

    public LiveData<ShopItem> getShopItemLD() {
        return shopItemLD;
    }

    public LiveData<Boolean> getErrorInputCount() {
        return errorInputCount;
    }

    public LiveData<Boolean> getErrorInputName() {
        return errorInputName;
    }

    public void getShopItem(Integer shopItemId) {
        ShopItem item = getShopItemUseCase.getShopItem(shopItemId);
        shopItemLD.setValue(item);
    }

    public void addShopItem(String inputName, String inputCount) {
        String name = parseName(inputName);
        Integer count = parseCount(inputCount);
        boolean fieldsValid = validateInput(name, count);
        if (fieldsValid) {
            ShopItem newItem = new ShopItem(name, count, true);
            addShopItemUseCase.addShopItem(newItem);
            finishWork();
        }
    }

    public void editShopItem(String inputName, String inputCount) {
        String name = parseName(inputName);
        Integer count = parseCount(inputCount);
        boolean fieldsValid = validateInput(name, count);
        if (fieldsValid) {
            ShopItem item = shopItemLD.getValue();
            if (item != null) {
                ShopItem newItem = new ShopItem(name, count, item.isEnabled());
                newItem.setId(item.getId());
                editShopItemUseCase.editShopItem(newItem);
                finishWork();
            }
        }
    }

    private String parseName(String name) {
        if (name != null) {
            return name.trim();
        } else return "";
    }

    private Integer parseCount(String count) {
        try {
            return Integer.parseInt(count.trim());
        } catch (Exception e) {
            return 0;
        }
    }


    private boolean validateInput(String name, Integer count) {
        boolean isCorrect = true;
        if (name.isEmpty()) {
            errorInputName.setValue(true);
            isCorrect = false;
        }
        if (count <= 0) {
            errorInputCount.setValue(true);
            isCorrect = false;
        }
        return isCorrect;
    }

    public void resetErrorInputName() {
        errorInputName.setValue(false);
    }

    public void resetErrorInputCount() {
        errorInputCount.setValue(false);
    }

    private void finishWork() {
        isFinished.setValue(new Object());
    }
}
