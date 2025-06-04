package com.restaurant.core;

import java.util.List;

public class DrinkFactory implements DishFactory {
    @Override
    public MenuItem createDish(String name, double price, List<String> ingredients) {
        return new Dish("Drink: " + name, price, ingredients);
    }
}