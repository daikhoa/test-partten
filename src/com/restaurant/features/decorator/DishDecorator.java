package com.restaurant.features.decorator;

import com.restaurant.core.MenuItem;

import java.util.List;

public interface DishDecorator extends MenuItem {
    @Override
    String getName();
    @Override
    double getPrice();
    @Override
    List<String> ingredients();
}