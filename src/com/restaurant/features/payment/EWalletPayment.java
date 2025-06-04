package com.restaurant.features.payment;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via E-Wallet");
    }

}
