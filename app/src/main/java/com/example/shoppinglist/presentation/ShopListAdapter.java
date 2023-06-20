package com.example.shoppinglist.presentation;

import static com.example.shoppinglist.Constants.STATE_DISABLED;
import static com.example.shoppinglist.Constants.STATE_ENABLED;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.shoppinglist.R;
import com.example.shoppinglist.domain.ShopItem;

public class ShopListAdapter extends ListAdapter<ShopItem, ShopItemViewHolder> {

    protected ShopListAdapter(@NonNull DiffUtil.ItemCallback<ShopItem> diffCallback) {
        super(diffCallback);
    }

//    private static final String TAG = "ShopListAdapter";
//    private int count = 0;

//    private List<ShopItem> shopList = new ArrayList<>();
//    public List<ShopItem> getShopList() {
//        return shopList;
//    }
//    public void setShopList(List<ShopItem> newShopList) {
//        DiffUtil.Callback callback = new ShopListDiffCallback(newShopList, shopList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
//        diffResult.dispatchUpdatesTo(this);
//        this.shopList = newShopList;
//    }

    private OnShopItemLongClickListener onShopItemLongClickListener;
    private OnShopItemClickListener onShopItemClickListener;

    public void setOnShopItemClickListener(OnShopItemClickListener onShopItemClickListener) {
        this.onShopItemClickListener = onShopItemClickListener;
    }

    public void setOnShopItemLongClickListener(OnShopItemLongClickListener onShopItemLongClickListener) {
        this.onShopItemLongClickListener = onShopItemLongClickListener;
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case STATE_ENABLED:
                layout = R.layout.item_shop_enabled;
                break;
            case STATE_DISABLED:
                layout = R.layout.item_shop_disabled;
                break;
            default:
                throw new RuntimeException(String.format("Unknown view type %s", viewType));
        }
        View view = LayoutInflater
                .from(parent.getContext()).inflate(layout,
                        parent,
                        false);
        return new ShopItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder holder, int position) {
        ShopItem item = getItem(position);

        holder.getTvTitle().setText(item.getName());
        holder.getTvCount().setText(String.valueOf(item.getCount()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onShopItemLongClickListener.onShopItemLongClick(item);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShopItemClickListener.onShopItemClick(item);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isEnabled()) return STATE_ENABLED;
        else return STATE_DISABLED;
    }

    interface OnShopItemLongClickListener {
        void onShopItemLongClick(ShopItem shopItem);
    }

    interface OnShopItemClickListener {
        void onShopItemClick(ShopItem shopItem);
    }


}
