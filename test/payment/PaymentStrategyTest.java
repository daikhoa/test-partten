package payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.restaurant.core.InvoiceManager;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import com.restaurant.features.payment.PaymentStrategy;
import com.restaurant.features.payment.CreditCardPayment;
import com.restaurant.features.payment.EWalletPayment;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentStrategyTest {

    private InvoiceManager invoiceManager;

    @BeforeEach
    public void setUp() {
        invoiceManager = InvoiceManager.getInstance();
    }

    @Test
    public void testCreditCardPayment() {
        // Kiểm tra CreditCardPayment in đúng thông báo
        PaymentStrategy paymentStrategy = new CreditCardPayment();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            paymentStrategy.pay(50.0);
            String output = outContent.toString();
            assertTrue(output.contains("Paid $50.0 via Credit Card"), "CreditCardPayment should print correct message");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testEWalletPayment() {
        // Kiểm tra EWalletPayment in đúng thông báo
        PaymentStrategy paymentStrategy = new EWalletPayment();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            paymentStrategy.pay(30.0);
            String output = outContent.toString();
            assertTrue(output.contains("Paid $30.0 via E-Wallet"), "EWalletPayment should print correct message");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testInvoiceWithCreditCardPayment() {
        // Kiểm tra tích hợp CreditCardPayment với InvoiceManager
        Order order = new Order();
        MenuItem dish = new Dish("Steak", 20.0, Arrays.asList("Beef", "Salt"));
        order.addDish(dish);
        PaymentStrategy paymentStrategy = new CreditCardPayment();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            invoiceManager.generateInvoice(order, paymentStrategy);
            String output = outContent.toString();
            assertTrue(output.contains("Invoice - Total: $20.0"), "Invoice should display correct total");
            assertTrue(output.contains("Paid $20.0 via Credit Card"), "CreditCardPayment should be called");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testInvoiceWithEWalletPayment() {
        // Kiểm tra tích hợp EWalletPayment với InvoiceManager
        Order order = new Order();
        MenuItem dish = new Dish("Steak", 20.0, Arrays.asList("Beef", "Salt"));
        order.addDish(dish);
        PaymentStrategy paymentStrategy = new EWalletPayment();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            invoiceManager.generateInvoice(order, paymentStrategy);
            String output = outContent.toString();
            assertTrue(output.contains("Invoice - Total: $20.0"), "Invoice should display correct total");
            assertTrue(output.contains("Paid $20.0 via E-Wallet"), "EWalletPayment should be called");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testSwitchPaymentStrategy() {
        // Kiểm tra hoán đổi chiến lược thanh toán
        Order order = new Order();
        MenuItem dish = new Dish("Steak", 20.0, Arrays.asList("Beef", "Salt"));
        order.addDish(dish);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Thanh toán bằng CreditCard
            PaymentStrategy creditCard = new CreditCardPayment();
            invoiceManager.generateInvoice(order, creditCard);
            String output1 = outContent.toString();
            assertTrue(output1.contains("Paid $20.0 via Credit Card"), "CreditCardPayment should be called");

            // Reset output
            outContent.reset();

            // Thanh toán bằng EWallet
            PaymentStrategy eWallet = new EWalletPayment();
            invoiceManager.generateInvoice(order, eWallet);
            String output2 = outContent.toString();
            assertTrue(output2.contains("Paid $20.0 via E-Wallet"), "EWalletPayment should be called");
        } finally {
            System.setOut(originalOut);
        }
    }
}