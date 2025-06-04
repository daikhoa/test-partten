package com.restaurant.core;

import com.restaurant.features.observer.CustomerObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderManager {
    private static OrderManager instance;
    private List<Order> orders = new ArrayList<>();
    private List<CustomerObserver> observers = new ArrayList<>();

    private OrderManager() {}

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
        notifyObservers();
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void registerObserver(CustomerObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (CustomerObserver observer : observers) {
            observer.update();
        }
    }

    public void displayMenu() {
        System.out.println("=== Thực đơn ===");
        DishFactory mainFactory = new MainCourseFactory();
        DishFactory dessertFactory = new DessertFactory();
        List<MenuItem> sampleMenu = Arrays.asList(
            mainFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")),
            mainFactory.createDish("Pasta", 12.0, Arrays.asList("Flour", "Cheese")),
            dessertFactory.createDish("Cake", 5.0, Arrays.asList("Flour", "Sugar"))
        );
        for (int i = 0; i < sampleMenu.size(); i++) {
            System.out.println((i + 1) + ". " + sampleMenu.get(i).getName() + " ($" + sampleMenu.get(i).getPrice() + ")");
        }
    }
    public void reset() {
        orders.clear();
        observers.clear();
    }
}