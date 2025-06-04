package com.restaurant.core;

import java.util.List;

public class Dish implements MenuItem {
    private String name;
    private double price;
    private List<String> ingredients;

    public Dish(String name, double price, List<String> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    @Override
    public String getName() { return name; }
    @Override
    public double getPrice() { return price; }
    @Override
    public List<String> ingredients() { return ingredients; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}