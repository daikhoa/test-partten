package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.restaurant.core.OrderManager;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class sigletonTest {

    private OrderManager manager;

    @BeforeEach
    public void setUp() {
        manager = OrderManager.getInstance();
        manager.reset(); // Reset trạng thái trước mỗi test
    }

    @Test
    public void testSingletonInstance() {
        OrderManager instance1 = OrderManager.getInstance();
        OrderManager instance2 = OrderManager.getInstance();
        assertSame(instance1, instance2, "OrderManager instances should be the same");
    }

    @Test
    public void testThreadSafety() throws InterruptedException {
        final int THREAD_COUNT = 10;
        OrderManager[] instances = new OrderManager[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = OrderManager.getInstance();
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 1; i < THREAD_COUNT; i++) {
            assertSame(instances[0], instances[i], "OrderManager instances should be the same in multi-thread");
        }
    }

    @Test
    public void testAddOrder() {
        Order order = new Order();
        MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
        order.addDish(dish);
        manager.addOrder(order);

        assertEquals(1, manager.getOrders().size(), "Order list should contain one order");
        assertEquals(order, manager.getOrders().get(0), "Added order should match");
        assertEquals(10.0, order.getTotal(), "Order total should be 10.0");
    }
}