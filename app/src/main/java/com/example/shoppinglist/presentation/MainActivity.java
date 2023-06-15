package com.example.shoppinglist.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.shoppinglist.R;
import com.example.shoppinglist.domain.ShopItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTest";
    private int count = 0;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getShopList().observe(this, new Observer<List<ShopItem>>() {
            @Override
            public void onChanged(List<ShopItem> shopItems) {
                Log.d(TAG, shopItems.toString());
                if (count==0) {
                    count++;
                    ShopItem item = shopItems.get(0);
                    viewModel.changeEnableState(item);
                }
            }
        });
    }
}