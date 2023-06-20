package com.example.shoppinglist.presentation;

import static com.example.shoppinglist.Constants.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.shoppinglist.R;
import com.example.shoppinglist.domain.ShopItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShopItemActivity extends AppCompatActivity {

    private String screenMode = MODE_UNKNOWN;
    private int shopItemId = UNDEFINED_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item);
        parseIntent();
        if (savedInstanceState==null) {
            launchRightMode();
        }
    }


    private void launchRightMode() {
        ShopItemFragment fragment;
        switch (screenMode) {
            case MODE_EDIT:
                fragment = ShopItemFragment.newInstanceEditItem(shopItemId);
                break;
            case MODE_ADD:
                fragment = ShopItemFragment.newInstanceAddItem();
                break;
            default:
                throw new RuntimeException("Unknown screen mode " + screenMode);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .commit();
    }

    public static Intent newIntentAddItem(Context context) {
        Intent intent = new Intent(context, ShopItemActivity.class);
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD);
        return intent;
    }

    public static Intent newIntentEditItem(Context context, Integer shopItemId) {
        Intent intent = new Intent(context, ShopItemActivity.class);
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT);
        intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId);
        return intent;
    }

    private void parseIntent() {
        if (!getIntent().hasExtra(EXTRA_SCREEN_MODE)) {
            throw new RuntimeException("Param screen mode is absent");
        }
        String mode = getIntent().getStringExtra(EXTRA_SCREEN_MODE);
        if (!mode.equals(MODE_ADD) && !mode.equals(MODE_EDIT)) {
            throw new RuntimeException("Unknown screen mode " + mode);
        }
        screenMode = mode;
        if (screenMode.equals(MODE_EDIT)) {
            if (!getIntent().hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw new RuntimeException("Param shop item is absent");
            }
            shopItemId = getIntent().getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID);
        }
    }
}