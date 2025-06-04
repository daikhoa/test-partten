package com.restaurant.core;

import java.util.List;

public interface DishFactory {
    MenuItem createDish(String name, double price, List<String> ingredients);
}