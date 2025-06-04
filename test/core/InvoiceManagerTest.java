package core;
import org.junit.jupiter.api.Test;
import com.restaurant.core.InvoiceManager;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import com.restaurant.features.payment.PaymentStrategy;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceManagerTest {

    // Giả lập PaymentStrategy
    private static class MockPaymentStrategy implements PaymentStrategy {
        private double paidAmount;
        private boolean paid;

        @Override
        public void pay(double amount) {
            this.paidAmount = amount;
            this.paid = true;
        }

        public double getPaidAmount() {
            return paidAmount;
        }

        public boolean isPaid() {
            return paid;
        }
    }

    @Test
    public void testGenerateInvoice() {
        InvoiceManager manager = InvoiceManager.getInstance();
        Order order = new Order();
        MenuItem dish = new Dish("TestDish", 20.0, Arrays.asList("TestIngredient"));
        order.addDish(dish);
        MockPaymentStrategy paymentStrategy = new MockPaymentStrategy();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            manager.generateInvoice(order, paymentStrategy);
            String output = outContent.toString();
            assertTrue(output.contains("Invoice - Total: $20.0"), "Invoice should display correct total");
            assertTrue(paymentStrategy.isPaid(), "Payment strategy should be called");
            assertEquals(20.0, paymentStrategy.getPaidAmount(), "Payment amount should match order total");
        } finally {
            System.setOut(originalOut);
        }
    }
}