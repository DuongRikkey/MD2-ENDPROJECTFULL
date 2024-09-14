package business.entity;

import business.features.IODATA;
import presentation.run.Main;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class OrderDetails implements Serializable, IODATA<OrderDetails,String> {
    private int orderId;
    private int productId;
    private String name;
    private double unit_price;
    private int order_quantity;

    public OrderDetails() {
    }

    public OrderDetails(int orderId, int productId, String name, double unit_price, int order_quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.unit_price = unit_price;
        this.order_quantity = order_quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    @Override
    public void inputData(Scanner sc, boolean isAdd) {
    }


    @Override
    public void displayData() {
        Locale locale=new Locale("vi","VN");
        NumberFormat currencyFomartter=NumberFormat.getCurrencyInstance(locale);
        final String PURPLE = "\033[35m";    // Tím cho đường viền
        final String YELLOW = "\033[33m";    // Vàng cho tiêu đề
        final String RESET = "\033[0m";      // Khôi phục màu mặc định

        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + YELLOW + "                         Order Detail                                  " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-8s " + PURPLE + "┃" + YELLOW + " %-10s " + PURPLE + "┃" + YELLOW + " %-13s " + PURPLE + "┃" + YELLOW + " %-11s " + PURPLE + "┃" + YELLOW + " %-15s " + PURPLE + "┃\n",
                "Order ID", "Product ID", "Product Name", "Unit Price", "Order Quantity");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Dòng chứa dữ liệu
        System.out.printf("┃" + PURPLE + " %-8d " + PURPLE + "┃" + PURPLE + " %-10d " + PURPLE + "┃" + PURPLE + " %-13s " + PURPLE + "┃"
                        + PURPLE + " %-11s " + PURPLE + "┃" + YELLOW + " %-15d " + PURPLE + "┃\n",
                this.orderId, this.productId, this.name, currencyFomartter.format(this.unit_price) , this.order_quantity);

        // Kết thúc bảng
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }

}
