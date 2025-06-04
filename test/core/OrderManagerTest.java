package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.restaurant.core.OrderManager;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import com.restaurant.features.observer.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

   public class OrderManagerTest {

       private OrderManager manager;

       @BeforeEach
       public void setUp() {
           manager = OrderManager.getInstance();
           manager.reset(); // Reset trạng thái trước mỗi test
       }

       // Giả lập CustomerObserver
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

       @Test
       public void testRegisterAndNotifyObserver() {
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
           MockCustomerObserver observer1 = new MockCustomerObserver();
           MockCustomerObserver observer2 = new MockCustomerObserver();
           manager.registerObserver(observer1);
           manager.registerObserver(observer2);

           Order order = new Order();
           MenuItem dish = new Dish("TestDish", 10.0, Arrays.asList("TestIngredient"));
           order.addDish(dish);
           manager.addOrder(order);

           assertTrue(observer1.isUpdated(), "Observer 1 should be notified");
           assertTrue(observer2.isUpdated(), "Observer 2 should be notified");
       }

       @Test
       public void testDisplayMenu() {
           ByteArrayOutputStream outContent = new ByteArrayOutputStream();
           PrintStream originalOut = System.out;
           System.setOut(new PrintStream(outContent));

           try {
               manager.displayMenu();
               String output = outContent.toString();
               assertTrue(output.contains("=== Thực đơn ==="), "Menu should start with header");
               assertTrue(output.contains("1. Main: Steak ($15.0)"), "Menu should contain Steak");
               assertTrue(output.contains("2. Main: Pasta ($12.0)"), "Menu should contain Pasta");
               assertTrue(output.contains("3. Dessert: Cake ($5.0)"), "Menu should contain Cake");
           } finally {
               System.setOut(originalOut);
           }
       }
   }