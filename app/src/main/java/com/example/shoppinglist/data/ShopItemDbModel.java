package com.example.shoppinglist.data;

import static com.example.shoppinglist.Constants.UNDEFINED_ID;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "shop_items")
public class ShopItemDbModel {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private int count;
    private boolean enabled;

    public ShopItemDbModel(String name, int count, boolean enabled) {
        this.name = name;
        this.count = count;
        this.enabled = enabled;
        this.id = UNDEFINED_ID;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopItemDbModel that = (ShopItemDbModel) o;
        return count == that.count && enabled == that.enabled && Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, enabled, id);
    }

    @Override
    public String toString() {
        return "ShopItemDbModel{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", enabled=" + enabled +
                ", id=" + id +
                '}';
    }
}

