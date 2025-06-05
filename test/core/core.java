package core;

import com.restaurant.core.OrderManager;
import com.restaurant.core.InvoiceManager;
import com.restaurant.core.DishFactory;
import com.restaurant.core.MainCourseFactory;
import com.restaurant.core.DessertFactory;
import com.restaurant.core.Order;
import com.restaurant.core.MenuItem;
import com.restaurant.features.observer.CustomerObserver;
import com.restaurant.features.payment.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MockPaymentStrategy implements PaymentStrategy {
    private double paidAmount;

    @Override
    public void pay(double amount) {
        this.paidAmount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }
}

class MockCustomerObserver implements CustomerObserver {
    private int updateCount = 0;

    @Override
    public void update() {
        updateCount++;
    }

    public int getUpdateCount() {
        return updateCount;
    }
}

public class core {

    private OrderManager orderManager;
    private InvoiceManager invoiceManager;
    private DishFactory mainCourseFactory;
    private DishFactory dessertFactory;

    @BeforeEach
    void setUp() {
        orderManager = OrderManager.getInstance();
        invoiceManager = InvoiceManager.getInstance();
        mainCourseFactory = new MainCourseFactory();
        dessertFactory = new DessertFactory();
        orderManager.reset(); // Đặt lại trạng thái để tránh ảnh hưởng giữa các test
    }

    // Kiểm tra mẫu Singleton: Đảm bảo OrderManager chỉ tạo ra một thể hiện duy nhất
    @Test
    void testSingletonPattern_OrderManager() {
        OrderManager instance1 = OrderManager.getInstance();
        OrderManager instance2 = OrderManager.getInstance();
        assertSame(instance1, instance2, "Các thể hiện của OrderManager phải giống nhau (Singleton)");
    }

    // Kiểm tra mẫu Singleton: Đảm bảo InvoiceManager chỉ tạo ra một thể hiện duy nhất
    @Test
    void testSingletonPattern_InvoiceManager() {
        InvoiceManager instance1 = InvoiceManager.getInstance();
        InvoiceManager instance2 = InvoiceManager.getInstance();
        assertSame(instance1, instance2, "Các thể hiện của InvoiceManager phải giống nhau (Singleton)");
    }

    // Kiểm tra mẫu Factory: Đảm bảo MainCourseFactory tạo đúng món ăn chính với tiền tố "Main: " và các thuộc tính hợp lệ
    @Test
    void testFactoryPattern_MainCourseCreation() {
        MenuItem steak = mainCourseFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt"));
        assertEquals("Main: Steak", steak.getName(), "MainCourseFactory phải tạo món ăn với tiền tố 'Main: '");
        assertEquals(15.0, steak.getPrice(), "Giá phải khớp với giá đầu vào");
        assertEquals(Arrays.asList("Beef", "Salt"), steak.ingredients(), "Nguyên liệu phải khớp với đầu vào");
    }

    // Kiểm tra mẫu Factory: Đảm bảo DessertFactory tạo đúng món tráng miệng với tiền tố "Dessert: " và các thuộc tính hợp lệ
    @Test
    void testFactoryPattern_DessertCreation() {
        MenuItem cake = dessertFactory.createDish("Cake", 5.0, Arrays.asList("Flour", "Sugar"));
        assertEquals("Dessert: Cake", cake.getName(), "DessertFactory phải tạo món ăn với tiền tố 'Dessert: '");
        assertEquals(5.0, cake.getPrice(), "Giá phải khớp với giá đầu vào");
        assertEquals(Arrays.asList("Flour", "Sugar"), cake.ingredients(), "Nguyên liệu phải khớp với đầu vào");
    }

    // Kiểm tra mẫu Composite: Đảm bảo Order tính toán đúng tổng giá và quản lý danh sách món ăn
    @Test
    void testCompositePattern_OrderTotal() {
        Order order = new Order();
        order.addDish(mainCourseFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")));
        order.addDish(dessertFactory.createDish("Cake", 5.0, Arrays.asList("Flour", "Sugar")));

        assertEquals(20.0, order.getTotal(), "Tổng giá của đơn hàng phải bằng tổng giá các món ăn");
        assertEquals(2, order.getDishes().size(), "Đơn hàng phải chứa hai món ăn");
    }

    
}