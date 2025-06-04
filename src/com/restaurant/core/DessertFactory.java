package com.restaurant.core;

import java.util.List;

public class DessertFactory implements DishFactory {
    @Override
    public MenuItem createDish(String name, double price, List<String> ingredients) {
        return new Dish("Dessert: " + name, price, ingredients);
    }
}