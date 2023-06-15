package com.example.shoppinglist.domain;

import static com.example.shoppinglist.Constants.UNDEFINED_ID;

import com.example.shoppinglist.Constants;

import java.util.Objects;

public class ShopItem {
    private String name;
    private int count;
    private boolean enabled;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getId() {
        return id;
    }

    public ShopItem(String name, int count, boolean enabled) {
        this.name = name;
        this.count = count;
        this.enabled = enabled;
        this.id = UNDEFINED_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopItem shopItem = (ShopItem) o;
        return count == shopItem.count && enabled == shopItem.enabled && id == shopItem.id && Objects.equals(name, shopItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, enabled, id);
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", enabled=" + enabled +
                ", id=" + id +
                '}';
    }
}
