package com.restaurant.core;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<MenuItem> dishes = new ArrayList<>();
    private double total = 0;

    public void addDish(MenuItem dish) {
        dishes.add(dish);
        total += dish.getPrice();
    }

    public double getTotal() { return total; }
    public List<MenuItem> getDishes() { return dishes; }
}