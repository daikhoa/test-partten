package com.restaurant.features.decorator;

import com.restaurant.core.MenuItem;

import java.util.List;

public class AddonDecorator implements DishDecorator {
    private MenuItem dish;
    private String addon;
    private double addonPrice;

    public AddonDecorator(MenuItem dish, String addon, double addonPrice) {
        this.dish = dish;
        this.addon = addon;
        this.addonPrice = addonPrice;
    }

    @Override
    public double getPrice() {
        return dish.getPrice() + addonPrice;
    }

    @Override
    public String getName() {
        return dish.getName() + " with " + addon;
    }

    @Override
    public List<String> ingredients() {
        return dish.ingredients(); // Kế thừa danh sách nguyên liệu từ MenuItem
    }
}