package payment;

import com.restaurant.core.Order;
import com.restaurant.core.InvoiceManager;
import com.restaurant.core.MainCourseFactory;
import com.restaurant.core.DishFactory;
import com.restaurant.features.payment.CreditCardPayment;
import com.restaurant.features.payment.EWalletPayment;
import com.restaurant.features.payment.PaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentStrategyTest {

    private Order order;
    private InvoiceManager invoiceManager;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        invoiceManager = InvoiceManager.getInstance();
        order = new Order();
        DishFactory mainCourseFactory = new MainCourseFactory();
        order.addDish(mainCourseFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")));
        // Chuyển hướng System.out để kiểm tra output của PaymentStrategy
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    
    @Test // Kiểm tra Strategy Pattern: Đảm bảo CreditCardPayment xử lý thanh toán và in đúng thông báo
    void testCreditCardPaymentStrategy() {
        PaymentStrategy paymentStrategy = new CreditCardPayment();
        invoiceManager.generateInvoice(order, paymentStrategy);

        // Chuẩn hóa output để bỏ qua khác biệt \r\n và \n
        String actualOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedOutput = "Invoice - Total: $15.0\nPaid $15.0 via Credit Card\n";
        assertEquals(expectedOutput, actualOutput, "CreditCardPayment phải in thông báo thanh toán đúng");
    }

    // Kiểm tra Strategy Pattern: Đảm bảo EWalletPayment xử lý thanh toán và in đúng thông báo
    @Test
    void testEWalletPaymentStrategy() {
        PaymentStrategy paymentStrategy = new EWalletPayment();
        invoiceManager.generateInvoice(order, paymentStrategy);

        // Chuẩn hóa output để bỏ qua khác biệt \r\n và \n
        String actualOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedOutput = "Invoice - Total: $15.0\nPaid $15.0 via E-Wallet\n";
        assertEquals(expectedOutput, actualOutput, "EWalletPayment phải in thông báo thanh toán đúng");
    }

    @Test // Kiểm tra Strategy Pattern: Đảm bảo InvoiceManager có thể sử dụng các PaymentStrategy khác nhau mà không thay đổi logic
    void testSwitchingPaymentStrategies() {
        // Test với CreditCardPayment
        PaymentStrategy creditCardStrategy = new CreditCardPayment();
        invoiceManager.generateInvoice(order, creditCardStrategy);
        String creditCardOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedCreditCardOutput = "Invoice - Total: $15.0\nPaid $15.0 via Credit Card\n";
        assertEquals(expectedCreditCardOutput, creditCardOutput, "CreditCardPayment phải in thông báo thanh toán đúng");
        
        // Reset output stream
        outputStream.reset();

        // Test với EWalletPayment
        PaymentStrategy eWalletStrategy = new EWalletPayment();
        invoiceManager.generateInvoice(order, eWalletStrategy);
        String eWalletOutput = outputStream.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        String expectedEWalletOutput = "Invoice - Total: $15.0\nPaid $15.0 via E-Wallet\n";
        assertEquals(expectedEWalletOutput, eWalletOutput, "EWalletPayment phải in thông báo thanh toán đúng");
    }
}