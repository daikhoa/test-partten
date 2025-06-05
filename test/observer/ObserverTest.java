package observer;

import com.restaurant.core.OrderManager;
import com.restaurant.core.Order;
import com.restaurant.core.MainCourseFactory;
import com.restaurant.core.DishFactory;
import com.restaurant.features.observer.MenuDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ObserverTest {

    private OrderManager orderManager;
    private MenuDisplay menuDisplay;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        orderManager = OrderManager.getInstance();
        menuDisplay = new MenuDisplay();
        orderManager.reset(); // Đặt lại trạng thái để tránh ảnh hưởng giữa các test
        // Chuyển hướng System.out để kiểm tra output của MenuDisplay
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test // Kiểm tra Observer Pattern: Đảm bảo MenuDisplay được thông báo và in đúng thông báo khi có đơn hàng mới
    void testMenuDisplayUpdate() {
        // Đăng ký MenuDisplay làm observer
        orderManager.registerObserver(menuDisplay);

        // Tạo đơn hàng mới để kích hoạt thông báo
        Order order = new Order();
        DishFactory mainCourseFactory = new MainCourseFactory();
        order.addDish(mainCourseFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")));
        orderManager.addOrder(order);

        // Chuẩn hóa output để bỏ qua khác biệt \r\n và \n
        String actualOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedOutput = "Menu updated! New order placed.\n";
        assertEquals(expectedOutput, actualOutput, "MenuDisplay phải in thông báo đúng khi được thông báo");
    }

    
    @Test // Kiểm tra Observer Pattern: Đảm bảo nhiều MenuDisplay observers được thông báo khi có đơn hàng mới
    void testMultipleMenuDisplayUpdates() {
        // Đăng ký hai MenuDisplay observers
        MenuDisplay menuDisplay2 = new MenuDisplay();
        orderManager.registerObserver(menuDisplay);
        orderManager.registerObserver(menuDisplay2);

        // Tạo đơn hàng mới để kích hoạt thông báo
        Order order = new Order();
        DishFactory mainCourseFactory = new MainCourseFactory();
        order.addDish(mainCourseFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")));
        orderManager.addOrder(order);

        // Chuẩn hóa output để bỏ qua khác biệt \r\n và \n
        String actualOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedOutput = "Menu updated! New order placed.\nMenu updated! New order placed.\n";
        assertEquals(expectedOutput, actualOutput, "Cả hai MenuDisplay observers phải in thông báo đúng");
    }

    @Test // Kiểm tra Observer Pattern: Đảm bảo MenuDisplay không in gì nếu không được thông báo
    void testNoUpdateWithoutOrder() {
        // Đăng ký MenuDisplay nhưng không thêm đơn hàng
        orderManager.registerObserver(menuDisplay);

        // Kiểm tra rằng không có output
        String actualOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("", actualOutput, "MenuDisplay không được in gì khi không có thông báo");
    }
}