package com.restaurant.core;

import java.util.List;

public class MainCourseFactory implements DishFactory {
    @Override
    public MenuItem createDish(String name, double price, List<String> ingredients) {
        return new Dish("Main: " + name, price, ingredients);
    }
}