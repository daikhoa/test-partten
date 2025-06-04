package core;
import org.junit.jupiter.api.Test;
import com.restaurant.core.DishFactory;
import com.restaurant.core.MainCourseFactory;
import com.restaurant.core.DessertFactory;
import com.restaurant.core.DrinkFactory;
import com.restaurant.core.MenuItem;
import com.restaurant.core.Dish;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTest {

    @Test
    public void testMainCourseFactory() {
        DishFactory factory = new MainCourseFactory();
        MenuItem dish = factory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt"));
        assertTrue(dish instanceof Dish, "Should create a Dish instance");
        assertEquals("Main: Steak", dish.getName(), "Dish name should include prefix");
        assertEquals(15.0, dish.getPrice(), "Dish price should be 15.0");
        assertEquals(Arrays.asList("Beef", "Salt"), dish.ingredients(), "Dish ingredients should match");
    }

    @Test
    public void testDessertFactory() {
        DishFactory factory = new DessertFactory();
        MenuItem dish = factory.createDish("Cake", 5.0, Arrays.asList("Flour", "Sugar"));
        assertTrue(dish instanceof Dish, "Should create a Dish instance");
        assertEquals("Dessert: Cake", dish.getName(), "Dish name should include prefix");
        assertEquals(5.0, dish.getPrice(), "Dish price should be 5.0");
        assertEquals(Arrays.asList("Flour", "Sugar"), dish.ingredients(), "Dish ingredients should match");
    }

    @Test
    public void testDrinkFactory() {
        DishFactory factory = new DrinkFactory();
        MenuItem dish = factory.createDish("Cola", 2.0, Arrays.asList("Water", "Sugar"));
        assertTrue(dish instanceof Dish, "Should create a Dish instance");
        assertEquals("Drink: Cola", dish.getName(), "Dish name should include prefix");
        assertEquals(2.0, dish.getPrice(), "Dish price should be 2.0");
        assertEquals(Arrays.asList("Water", "Sugar"), dish.ingredients(), "Dish ingredients should match");
    }
}