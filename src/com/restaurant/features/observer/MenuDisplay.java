package com.restaurant.features.observer;

public class MenuDisplay implements CustomerObserver {
    @Override
    public void update() {
        System.out.println("Menu updated! New order placed.");
    }

}
