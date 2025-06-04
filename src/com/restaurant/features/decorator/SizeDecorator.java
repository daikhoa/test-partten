package com.restaurant.features.decorator;

import com.restaurant.core.MenuItem;

import java.util.List;

public class SizeDecorator implements DishDecorator {
    private MenuItem dish;
    private String size;
    private double sizePrice;

    public SizeDecorator(MenuItem dish, String size, double sizePrice) {
        this.dish = dish;
        this.size = size;
        this.sizePrice = sizePrice;
    }

    @Override
    public double getPrice() {
        return dish.getPrice() + sizePrice;
    }

    @Override
    public String getName() {
        return size + " " + dish.getName();
    }

    @Override
    public List<String> ingredients() {
        return dish.ingredients(); // Kế thừa danh sách nguyên liệu từ MenuItem
    }
}