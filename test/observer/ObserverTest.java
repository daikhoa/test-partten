package observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.restaurant.core.OrderManager;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import com.restaurant.features.observer.CustomerObserver;
import com.restaurant.features.observer.MenuDisplay;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class ObserverTest {

    private OrderManager manager;

    // Giả lập CustomerObserver để test
    private static class MockCustomerObserver implements CustomerObserver {
        private boolean updated = false;

        @Override
        public void update() {
            updated = true;
        }

        public boolean isUpdated() {
            return updated;
        }
    }

    @BeforeEach
    public void setUp() {
        manager = OrderManager.getInstance();
        manager.reset(); // Reset trạng thái trước mỗi test
    }

    @Test
    public void testMenuDisplayUpdate() {
        // Kiểm tra MenuDisplay in đúng thông báo khi update
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            CustomerObserver menuDisplay = new MenuDisplay();
            manager.registerObserver(menuDisplay);
            Order order = new Order();
            MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
            order.addDish(dish);
            manager.addOrder(order);

            String output = outContent.toString();
            assertTrue(output.contains("Menu updated! New order placed."), "MenuDisplay should print update message");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testRegisterAndNotifyObserver() {
        // Kiểm tra đăng ký và thông báo một observer
        MockCustomerObserver observer = new MockCustomerObserver();
        manager.registerObserver(observer);
        Order order = new Order();
        MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
        order.addDish(dish);
        manager.addOrder(order);

        assertTrue(observer.isUpdated(), "Observer should be notified when order is added");
    }

    @Test
    public void testMultipleObservers() {
        // Kiểm tra thông báo nhiều observer
        MockCustomerObserver observer1 = new MockCustomerObserver();
        MockCustomerObserver observer2 = new MockCustomerObserver();
        MenuDisplay menuDisplay = new MenuDisplay();
        manager.registerObserver(observer1);
        manager.registerObserver(observer2);
        manager.registerObserver(menuDisplay);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Order order = new Order();
            MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
            order.addDish(dish);
            manager.addOrder(order);

            assertTrue(observer1.isUpdated(), "Observer 1 should be notified");
            assertTrue(observer2.isUpdated(), "Observer 2 should be notified");
            String output = outContent.toString();
            assertTrue(output.contains("Menu updated! New order placed."), "MenuDisplay should print update message");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testNoObservers() {
        // Kiểm tra khi không có observer
        Order order = new Order();
        MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
        order.addDish(dish);
        manager.addOrder(order);

        assertEquals(1, manager.getOrders().size(), "Order should be added even with no observers");
    }
}