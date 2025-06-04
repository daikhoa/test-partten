package decorator;

import org.junit.jupiter.api.Test;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import com.restaurant.features.decorator.AddonDecorator;
import com.restaurant.features.decorator.SizeDecorator;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DecoratorTest {

    private static final List<String> BASE_INGREDIENTS = Arrays.asList("Beef", "Salt");

    @Test
    public void testAddonDecorator() {
        // Test thêm addon vào món ăn
        MenuItem baseDish = new Dish("Steak", 15.0, BASE_INGREDIENTS);
        MenuItem decoratedDish = new AddonDecorator(baseDish, "Cheese", 2.0);

        assertEquals("Steak with Cheese", decoratedDish.getName(), "Name should include addon");
        assertEquals(17.0, decoratedDish.getPrice(), "Price should include addon price");
        assertEquals(BASE_INGREDIENTS, decoratedDish.ingredients(), "Ingredients should remain unchanged");
    }

    @Test
    public void testSizeDecorator() {
        // Test thêm kích thước vào món ăn
        MenuItem baseDish = new Dish("Steak", 15.0, BASE_INGREDIENTS);
        MenuItem decoratedDish = new SizeDecorator(baseDish, "Large", 3.0);

        assertEquals("Large Steak", decoratedDish.getName(), "Name should include size");
        assertEquals(18.0, decoratedDish.getPrice(), "Price should include size price");
        assertEquals(BASE_INGREDIENTS, decoratedDish.ingredients(), "Ingredients should remain unchanged");
    }

    @Test
    public void testCombinedDecorators() {
        // Test kết hợp addon và size
        MenuItem baseDish = new Dish("Steak", 15.0, BASE_INGREDIENTS);
        MenuItem addonDecorated = new AddonDecorator(baseDish, "Cheese", 2.0);
        MenuItem sizeDecorated = new SizeDecorator(addonDecorated, "Large", 3.0);

        assertEquals("Large Steak with Cheese", sizeDecorated.getName(), "Name should include both size and addon");
        assertEquals(20.0, sizeDecorated.getPrice(), "Price should include both addon and size");
        assertEquals(BASE_INGREDIENTS, sizeDecorated.ingredients(), "Ingredients should remain unchanged");
    }

    @Test
    public void testMultipleAddons() {
        // Test thêm nhiều addon
        MenuItem baseDish = new Dish("Steak", 15.0, BASE_INGREDIENTS);
        MenuItem firstAddon = new AddonDecorator(baseDish, "Cheese", 2.0);
        MenuItem secondAddon = new AddonDecorator(firstAddon, "Bacon", 3.0);

        assertEquals("Steak with Cheese with Bacon", secondAddon.getName(), "Name should include all addons");
        assertEquals(20.0, secondAddon.getPrice(), "Price should include all addon prices");
        assertEquals(BASE_INGREDIENTS, secondAddon.ingredients(), "Ingredients should remain unchanged");
    }

    @Test
    public void testSizeAndAddonOrder() {
        // Test thứ tự áp dụng decorator (size rồi addon)
        MenuItem baseDish = new Dish("Steak", 15.0, BASE_INGREDIENTS);
        MenuItem sizeDecorated = new SizeDecorator(baseDish, "Large", 3.0);
        MenuItem addonDecorated = new AddonDecorator(sizeDecorated, "Cheese", 2.0);

        assertEquals("Large Steak with Cheese", addonDecorated.getName(), "Name should reflect size then addon");
        assertEquals(20.0, addonDecorated.getPrice(), "Price should include both size and addon");
        assertEquals(BASE_INGREDIENTS, addonDecorated.ingredients(), "Ingredients should remain unchanged");
    }
}