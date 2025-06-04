package com.restaurant;

import com.restaurant.core.*;
import com.restaurant.features.payment.*;
import com.restaurant.features.decorator.*;
import com.restaurant.features.observer.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các thành phần
        OrderManager orderManager = OrderManager.getInstance();
        MenuDisplay menuDisplay = new MenuDisplay();
        orderManager.registerObserver(menuDisplay);
        Scanner scanner = new Scanner(System.in);

        // Tạo danh sách món ăn mẫu (thực đơn)
        DishFactory mainFactory = new MainCourseFactory();
        DishFactory dessertFactory = new DessertFactory();
        List<MenuItem> menu = Arrays.asList(
            mainFactory.createDish("Steak", 15.0, Arrays.asList("Beef", "Salt")),
            mainFactory.createDish("Pasta", 12.0, Arrays.asList("Flour", "Cheese")),
            dessertFactory.createDish("Cake", 5.0, Arrays.asList("Flour", "Sugar"))
        );

        // Xem thực đơn
        System.out.println("=== Thực đơn ===");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName() + " ($" + menu.get(i).getPrice() + ")");
        }

        // Đặt món
        Order order = new Order();
        while (true) {
            System.out.println("\nNhập số thứ tự món để đặt (1-" + menu.size() + ") hoặc 0 để kết thúc: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Xóa bộ đệm

            if (choice == 0) break;

            if (choice < 1 || choice > menu.size()) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            MenuItem selectedDish = menu.get(choice - 1);
            System.out.println("Bạn đã chọn: " + selectedDish.getName());

            // Tùy chỉnh món ăn
            System.out.println("Có muốn thêm gia vị không? (1: Có, 0: Không): ");
            int addonChoice = scanner.nextInt();
            scanner.nextLine();
            if (addonChoice == 1) {
                selectedDish = new AddonDecorator(selectedDish, "Spicy Sauce", 2.0);
            }

            System.out.println("Chọn kích cỡ (1: Nhỏ, 2: Lớn, 0: Bình thường): ");
            int sizeChoice = scanner.nextInt();
            scanner.nextLine();
            if (sizeChoice == 2) {
                selectedDish = new SizeDecorator(selectedDish, "Large", 3.0);
            } else if (sizeChoice == 1) {
                selectedDish = new SizeDecorator(selectedDish, "Small", -1.0);
            }

            // Thêm món đã tùy chỉnh vào đơn hàng
            order.addDish(selectedDish);
            System.out.println("Đã thêm " + selectedDish.getName() + " vào đơn hàng.");
        }

        // Kiểm tra và xử lý đơn hàng
        if (order.getDishes().isEmpty()) {
            System.out.println("Không có món nào trong đơn hàng. Kết thúc chương trình.");
            scanner.close();
            return;
        }

        // Hiển thị đơn hàng và tổng tiền
        System.out.println("\n=== Đơn hàng của bạn ===");
        for (MenuItem dish : order.getDishes()) {
            System.out.println("- " + dish.getName() + " ($" + dish.getPrice() + ")");
        }
        System.out.println("Tổng tiền: $" + order.getTotal());

        // Quản lý đơn hàng qua OrderManager
        orderManager.addOrder(order);

        // Thanh toán hóa đơn
        System.out.println("\nChọn phương thức thanh toán (1: Credit Card, 2: E-Wallet): ");
        int paymentChoice = scanner.nextInt();
        PaymentStrategy paymentStrategy = (paymentChoice == 1) ? new CreditCardPayment() : new EWalletPayment();
        InvoiceManager invoiceManager = InvoiceManager.getInstance();
        invoiceManager.generateInvoice(order, paymentStrategy);

        scanner.close();
    }
}