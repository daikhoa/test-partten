package decorator;

import com.restaurant.core.Dish;
import com.restaurant.core.MenuItem;
import com.restaurant.features.decorator.AddonDecorator;
import com.restaurant.features.decorator.SizeDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DecoratorTest{

    private MenuItem baseDish;

    @BeforeEach
    void setUp() {
        // Khởi tạo một món ăn cơ bản để test
        baseDish = new Dish("Steak", 15.0, Arrays.asList("Beef", "Salt"));
    }

    // Kiểm tra Decorator Pattern: Đảm bảo AddonDecorator thêm đúng addon vào tên và giá món ăn
    @Test
    void testAddonDecorator() {
        MenuItem decoratedDish = new AddonDecorator(baseDish, "Cheese", 2.0);
        assertEquals("Steak with Cheese", decoratedDish.getName(), "AddonDecorator phải thêm 'with Cheese' vào tên món ăn");
        assertEquals(17.0, decoratedDish.getPrice(), "AddonDecorator phải tăng giá món ăn lên 2.0");
        assertEquals(baseDish.ingredients(), decoratedDish.ingredients(), "Nguyên liệu phải giữ nguyên từ món ăn gốc");
    }

    // Kiểm tra Decorator Pattern: Đảm bảo SizeDecorator thêm đúng kích thước vào tên và giá món ăn
    @Test
    void testSizeDecorator() {
        MenuItem decoratedDish = new SizeDecorator(baseDish, "Large", 3.0);
        assertEquals("Large Steak", decoratedDish.getName(), "SizeDecorator phải thêm 'Large' vào trước tên món ăn");
        assertEquals(18.0, decoratedDish.getPrice(), "SizeDecorator phải tăng giá món ăn lên 3.0");
        assertEquals(baseDish.ingredients(), decoratedDish.ingredients(), "Nguyên liệu phải giữ nguyên từ món ăn gốc");
    }

    // Kiểm tra Decorator Pattern: Đảm bảo có thể xếp chồng nhiều decorator (Addon và Size) và tính toán đúng
    @Test
    void testStackedDecorators() {
        MenuItem decoratedDish = new SizeDecorator(
            new AddonDecorator(baseDish, "Cheese", 2.0), "Large", 3.0
        );
        assertEquals("Large Steak with Cheese", decoratedDish.getName(), "Stacked decorators phải kết hợp đúng tên: 'Large' và 'with Cheese'");
        assertEquals(20.0, decoratedDish.getPrice(), "Stacked decorators phải cộng dồn giá: 15.0 + 2.0 + 3.0");
        assertEquals(baseDish.ingredients(), decoratedDish.ingredients(), "Nguyên liệu phải giữ nguyên từ món ăn gốc");
    }
}