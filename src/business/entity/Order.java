package business.entity;

import business.constants.Payments;
import business.constants.Status;
import business.features.IODATA;
import business.features.impl.CartImpl;
import business.utils.InputMethod;
import presentation.run.Main;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order implements Serializable , IODATA<Order,String> {
    private int orderId,userId;
    private double totalPrice;
    private String serialNumber,note,receiveName,receiveAddress,receivePhone;
    private Date createdAt,receiveAt;
    private List<Cart> carts;
    private Status status;
    private Payments payments;

    public Order() {
    }

    public Order(int orderId, int userId, double totalPrice, String serialNumber, String note, String receiveName,
                 String receiveAddress, String receivePhone, Date createdAt, Date receiveAt,
                 List<Cart> carts, Status status, Payments payments) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.serialNumber = serialNumber;
        this.note = note;
        this.receiveName = receiveName;
        this.receiveAddress = receiveAddress;
        this.receivePhone = receivePhone;
        this.createdAt = createdAt;
        this.receiveAt = receiveAt;
        this.carts = carts;
        this.status = status;
        this.payments = payments;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getReceiveAt() {
        return receiveAt;
    }

    public void setReceiveAt(Date receiveAt) {
        this.receiveAt = receiveAt;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Payments getPayments() {
        return payments;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }

    @Override
    public void inputData(Scanner sc, boolean isAdd) {
    }
    public void inputDataOrder(Scanner sc, double totalPrice, Address address) {
        this.userId=Main.userLogin.getId();
        this.totalPrice=totalPrice;
        this.createdAt=new Date();
        this.serialNumber=inputSerialNumber();
        this.note=inputNote();
        this.receiveName=address.getReceive();
        this.receiveAddress=address.getFullAddress();
        this.receivePhone=address.getPhone();
        this.carts=inputCart();
        this.receiveAt=inputReceiveAt(this.createdAt);
        this.status=Status.WAITING;
    }

    private String inputSerialNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private Date inputReceiveAt(Date createdAt) {
        Calendar calendar = Calendar.getInstance();
//        Tạo một đối tượng Calendar đại diện cho thời gian hiện tại (thời gian hiện tại của hệ thống).
        calendar.setTime(createdAt);
//        Thiết lập thời gian của Calendar dựa trên giá trị của createdAt (ngày tạo đơn hàng)
        calendar.add(Calendar.DAY_OF_MONTH, 4);
//        Thêm 2 ngày vào ngày hiện tại (ngày tạo đơn hàng) để tính toán ngày giao hàng (giả sử thời gian giao hàng là 2 ngày sau khi tạo đơn hàng).
        Date deliveryDate = calendar.getTime();
//        Trả về ngày giao hàng dưới dạng đối tượng Date
        return deliveryDate;
    }

    private String inputNote() {
        System.out.println("Mời bạn nhập chú thích đơn hàng");
        String note= InputMethod.getString();
        return note;
    }

    private List<Cart> inputCart() {
        List<Cart> carts= CartImpl.cartList.stream().
                filter(cart -> cart.getUserId()==Main.userLogin.getId()).toList();
        return carts;

    }

    final String PURPLE = "\033[35m";  // Purple for borders
    final String CYAN = "\033[36m";    // Cyan for headers
    final String YELLOW = "\033[33m";  // Yellow for text
    final String GREEN = "\033[32m";   // Green for active status
    final String RED = "\033[31m";     // Red for inactive status
    final String RESET = "\033[0m";
    @Override
    public void displayData() {

            // Locale for formatting currency in VND
            Locale locale = new Locale("vi", "VN");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃" + CYAN + "                              ORDER                                    " + PURPLE +  "┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            // Order ID, User ID, Serial Number
            System.out.printf("┃" + YELLOW + " %-10s" + PURPLE + "┃" + YELLOW + " %-10s" + PURPLE + "┃" + YELLOW + " %-46s" + PURPLE + "┃\n",
                    "Order ID", "User ID", "Serial Number");
            System.out.printf("┃ %-9d " + PURPLE + "┃ %-9d " + PURPLE + "┃ %-45s " + PURPLE + "┃\n",
                    this.orderId, this.userId, this.serialNumber);

            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            // Total Price, Created At, Receive At
            System.out.printf("┃" + YELLOW + " %-16s" + PURPLE + "┃" + YELLOW + " %-16s" + PURPLE + "┃" + YELLOW + " %-34s" + PURPLE + "┃\n",
                    "Total Price", "Created At", "Receive At");
            System.out.printf("┃ %-15s " + PURPLE + "┃ %-15s " + PURPLE + "┃ %-33s " + PURPLE + "┃\n",
                    currencyFormatter.format(this.totalPrice),
                    this.createdAt != null ? dateFormat.format(this.createdAt) : "N/A",
                    this.receiveAt != null ? dateFormat.format(this.receiveAt) : "N/A");

            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            // Receive Name, Receive Address, Receive Phone
            System.out.printf("┃" + YELLOW + " %-20s" + PURPLE + "┃" + YELLOW + " %-48s" + PURPLE + "┃\n",
                    "Receive Name", "Receive Address");
            System.out.printf("┃ %-19s " + PURPLE + "┃ %-47s " + PURPLE + "┃\n",
                    this.receiveName, this.receiveAddress);

            System.out.printf("┃" + YELLOW + " %-20s" + YELLOW + "┃ %-48s" + PURPLE+ "┃\n",
                    "Receive Phone", "Note");
            System.out.printf("┃ %-19s " + PURPLE + "┃ %-47s " + PURPLE + "┃\n",
                    this.receivePhone, this.note);

            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            // Status, Payment Method
            System.out.printf("┃" + YELLOW + " %-20s" + PURPLE + "┃" + YELLOW + " %-48s" + PURPLE + "┃\n",
                    "Status", "Payment Method");
            System.out.printf("┃ %-19s " + PURPLE + "┃ %-47s " + PURPLE + "┃\n",
                    this.status != null ? this.status.name() : "N/A",
                    this.payments != null ? this.payments.name() : "N/A");

            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }
        public void displayDataForUser() {

        }
}

