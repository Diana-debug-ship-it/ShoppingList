package com.example.shoppinglist.presentation;

import static com.example.shoppinglist.Constants.MAX_POOL_SIZE;
import static com.example.shoppinglist.Constants.STATE_DISABLED;
import static com.example.shoppinglist.Constants.STATE_ENABLED;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppinglist.R;
import com.example.shoppinglist.domain.ShopItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private ShopListAdapter adapter;
    private RecyclerView recyclerViewShopItems;

    private FloatingActionButton btnAddItem;
    private FragmentContainerView shopItemContainer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shopItemContainer = findViewById(R.id.shop_item_container);
        btnAddItem = findViewById(R.id.buttonAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOneFrameMode()) {
                    Intent intent = ShopItemActivity.newIntentAddItem(MainActivity.this);
                    startActivity(intent);
                } else {
                    launchFragment(ShopItemFragment.newInstanceAddItem());
                }
            }
        });

        setupRecyclerView();
        setSwipeToDelete();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getShopList().observe(this, new Observer<List<ShopItem>>() {
            @Override
            public void onChanged(List<ShopItem> shopItems) {
                adapter.submitList(shopItems);
            }
        });
    }

    private boolean isOneFrameMode() {
        return shopItemContainer == null;
    }

    private void launchFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.shop_item_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupRecyclerView() {
        recyclerViewShopItems = findViewById(R.id.recyclerViewItems);
        adapter = new ShopListAdapter(new ShopItemDiffCallback());
        recyclerViewShopItems.setAdapter(adapter);
        recyclerViewShopItems.setItemAnimator(null);
        recyclerViewShopItems.getRecycledViewPool().setMaxRecycledViews(STATE_ENABLED, MAX_POOL_SIZE);
        recyclerViewShopItems.getRecycledViewPool().setMaxRecycledViews(STATE_DISABLED, MAX_POOL_SIZE);

        adapter.setOnShopItemLongClickListener(new ShopListAdapter.OnShopItemLongClickListener() {
            @Override
            public void onShopItemLongClick(ShopItem shopItem) {
                viewModel.changeEnableState(shopItem);
            }
        });


        adapter.setOnShopItemClickListener(new ShopListAdapter.OnShopItemClickListener() {
            @Override
            public void onShopItemClick(ShopItem shopItem) {
                if (isOneFrameMode()) {
                    Intent intent = ShopItemActivity.newIntentEditItem(MainActivity.this, shopItem.getId());
                    startActivity(intent);
                } else {
                    launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.getId()));
                }
            }
        });
    }

    private void setSwipeToDelete() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ShopItem shopItem = adapter.getCurrentList().get(position);
                viewModel.deleteShopItem(shopItem);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewShopItems);
    }


}