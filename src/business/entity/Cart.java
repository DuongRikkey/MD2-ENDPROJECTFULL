package business.entity;

import business.features.ICart;
import business.features.IODATA;
import business.features.impl.CartImpl;
import business.features.impl.CategoryImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.InputMethod;
import presentation.run.Main;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Cart implements Serializable, IODATA<Cart,String> {
    private int id;
    private int quantity;
    private int userId;
    private Product product;
    private double totalPrice;

    public Cart() {
    }

    public Cart(int id, int quantity, int userId, Product product, double totalPrice) {
        this.id = id;
        this.quantity = quantity;
        this.userId = userId;
        this.product = product;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private Product inputProduct(int productId) {
       Optional<Product> optionalProduct=ProductImpl.productList.stream().filter(p->p.getProductId()==productId).findFirst();
       if(optionalProduct.isPresent()) {
           return optionalProduct.get();
       }
       return null;
    }


    @Override
    public void inputData(Scanner sc, boolean isAdd) {
    }
    private int inputQuantity(Product product) {
        do {
            System.out.println("Mời bạn nhập số lượng sản phẩm ");
           int quantity=InputMethod.getInteger();
            if (quantity>0) {
                if (quantity>product.getStockQuantity()) {
                    System.err.println("Số lượng đã vượt quá kho hàng");
                }else {
                    return quantity;
                }
            }else {
                System.err.println("Bạn phải nhập số lượng sản phẩm muốn thêm vào lớn hơn 0");
            }




        }while (true);
    }


    public void inputDataCart(Scanner sc, int productId) {
        this.userId=Main.userLogin.getId();
        this.product=inputProduct(productId);
        this.quantity=inputQuantity(this.product);
        this.totalPrice=this.quantity*this.product.getUnitPrice();
    }
    @Override
        public void displayData() {
            final String PURPLE = "\033[35m";    // Tím cho đường viền
            final String YELLOW = "\033[33m";   // Vàng cho tiêu đề
            final String RESET = "\033[0m";     // Khôi phục màu mặc định
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // In tiêu đề bảng
        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + PURPLE+ "  Cart ID  " + PURPLE + "┃" + PURPLE+ "  User ID  " + PURPLE + "┃" + PURPLE+ "  Quantity  " + PURPLE + "┃"
                + PURPLE+ "  Product Name   " + PURPLE + "┃" + PURPLE+ "   Price           " + PURPLE + "┃" + PURPLE + "  Total Price     "+ PURPLE +      "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

// Dòng chứa dữ liệu
        System.out.printf("┃" + YELLOW + " %-9d " + PURPLE + "┃" + YELLOW + " %-9d " + PURPLE + "┃" + YELLOW + " %-10d " + PURPLE + "┃"
                        + YELLOW + " %-15s " + PURPLE + "┃" + YELLOW + " %-17s " + PURPLE + "┃" + YELLOW + " %-16s " + PURPLE + "┃\n",
                this.id, this.userId, this.quantity, this.product.getProductName(),
                currencyFormatter.format(this.product.getUnitPrice()),
                currencyFormatter.format(this.product.getUnitPrice() * this.quantity));

// Kết thúc bảng
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);


    }
}

