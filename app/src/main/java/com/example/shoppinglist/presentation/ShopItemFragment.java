package com.example.shoppinglist.presentation;

import static com.example.shoppinglist.Constants.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppinglist.R;
import com.example.shoppinglist.domain.ShopItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShopItemFragment extends Fragment {

    private TextInputLayout tilName;
    private TextInputLayout tilCount;
    private TextInputEditText editTextName;
    private TextInputEditText editTextCount;
    private ShopItemViewModel viewModel;
    private Button buttonSave;

    private String screenMode = MODE_UNKNOWN;
    private int shopItemId = UNDEFINED_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseParams();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShopItemViewModel.class);
        initViews(view);
        launchRightMode();
        setupTextWatchers();
        observeViewModel();
    }
    
    public static ShopItemFragment newInstanceAddItem() {
        Bundle args = new Bundle();
        args.putString(EXTRA_SCREEN_MODE, MODE_ADD);
        ShopItemFragment fragment = new ShopItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShopItemFragment newInstanceEditItem(Integer shopItemId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_SCREEN_MODE, MODE_EDIT);
        args.putInt(EXTRA_SHOP_ITEM_ID, shopItemId);
        ShopItemFragment fragment = new ShopItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw new RuntimeException("Param screen mode is absent");
        }
        String mode = args.getString(EXTRA_SCREEN_MODE);
        if (!mode.equals(MODE_ADD) && !mode.equals(MODE_EDIT)) {
            throw new RuntimeException("Unknown screen mode " + mode);
        }
        screenMode = mode;
        if (screenMode.equals(MODE_EDIT)) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw new RuntimeException("Param shop item is absent");
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID);
        }
    }

    private void observeViewModel() {
        viewModel.getErrorInputCount().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                String message;
                if (isError) {
                    message = getString(R.string.error_input_count);
                } else {
                    message = null;
                }
                tilCount.setError(message);
            }
        });

        viewModel.getErrorInputName().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                String message;
                if (isError) {
                    message = getString(R.string.error_input_name);
                } else {
                    message = null;
                }
                tilName.setError(message);
            }
        });


        viewModel.getIsFinished().observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                getActivity().onBackPressed();
            }
        });
    }

    private void launchRightMode() {
        switch (screenMode) {
            case MODE_EDIT:
                launchEditMode();
                break;
            case MODE_ADD:
                launchAddMode();
                break;
        }
    }

    private void setupTextWatchers() {
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.resetErrorInputName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.resetErrorInputCount();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void launchEditMode() {
        viewModel.getShopItem(shopItemId);
        viewModel.getShopItemLD().observe(getViewLifecycleOwner(), new Observer<ShopItem>() {
            @Override
            public void onChanged(ShopItem shopItem) {
                editTextName.setText(shopItem.getName());
                editTextCount.setText(String.valueOf(shopItem.getCount()));
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.editShopItem(editTextName.getText().toString(), editTextCount.getText().toString());
            }
        });
    }

    private void launchAddMode() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addShopItem(editTextName.getText().toString(), editTextCount.getText().toString());
            }
        });
    }

    private void initViews(View view) {
        tilName = view.findViewById(R.id.til_name);
        tilCount = view.findViewById(R.id.til_count);
        editTextName = view.findViewById(R.id.ti_editText_name);
        editTextCount = view.findViewById(R.id.ti_editText_count);
        buttonSave = view.findViewById(R.id.btnSave);
    }
}
