package com.restaurant.core;

import com.restaurant.features.payment.PaymentStrategy;

public class InvoiceManager {
    private static InvoiceManager instance;

    private InvoiceManager() {}

    public static InvoiceManager getInstance() {
        if (instance == null) {
            instance = new InvoiceManager();
        }
        return instance;
    }

    public void generateInvoice(Order order, PaymentStrategy paymentStrategy) {
        double total = order.getTotal();
        System.out.println("Invoice - Total: $" + total);
        paymentStrategy.pay(total);
    }
}