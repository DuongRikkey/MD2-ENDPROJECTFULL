package business.entity;

import business.features.IODATA;
import business.utils.InputMethod;
import presentation.run.Main;

import java.io.Serializable;
import java.util.Scanner;

public class Address implements Serializable, IODATA<Address,String> {
    private int addressId;
    private int userId;
    private String fullAddress;
    private String phone;
    private String receiveName;
    public Address() {}

    public Address(int addressId, int userId, String fullAddress, String phone, String receiveName) {
        this.addressId = addressId;
        this.userId = userId;
        this.fullAddress = fullAddress;
        this.phone = phone;
        this.receiveName = receiveName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getReceive() {
        return receiveName;
    }

    public void setReceive(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void inputData(Scanner sc, boolean isAdd) {
        this.userId = Main.userLogin.getId();
        this.fullAddress = inputAddress(sc);
        this.phone = inputPhone();
        this.receiveName=inputReceiveName(sc);

    }

    private String inputReceiveName(Scanner sc) {
        System.out.println("Mời bạn nhập tên người nhận");
        String receiveName = InputMethod.getString();
        return receiveName;
    }

    private String inputPhone() {
        String regionVN="84";
        String phoneRegax="";
        while (true){
            System.out.println("Mời bạn nhập số điện thoại");
            long phone=InputMethod.getLong();
            phoneRegax=String.valueOf(phone);
            if (phoneRegax.length()==9){
                break;
            }
            else {
                System.err.println("Số điện thoại phải 10 chữ số");
            }
        }
        return regionVN+phoneRegax;
    }

    private String inputAddress(Scanner sc) {
        System.out.println("Mời bạn nhập địa chỉ của bạn");
            String address = InputMethod.getString();
            return address;}
    final String PURPLE = "\033[35m";  // Purple for borders
    final String CYAN = "\033[36m";    // Cyan for headers
    final String YELLOW = "\033[33m";  // Yellow for text
    final String GREEN = "\033[32m";   // Green for active status
    final String RED = "\033[31m";     // Red for inactive status
    final String RESET = "\033[0m";    // Reset to default color

    @Override
    public void displayData() {
        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + CYAN + "                         ADDRESS INFORMATION                           " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

// Row 1: Address ID, User ID, Full Address
        System.out.printf("┃" + YELLOW + " %-10s" + PURPLE + "┃" + YELLOW + " %-10s" + PURPLE + "┃" + YELLOW + " %-46s" + PURPLE + "┃\n",
                "Address ID", "User ID", "Full Address");
        System.out.printf("┃ %-9d " + PURPLE + "┃ %-9d " + PURPLE + "┃ %-45s " + PURPLE + "┃\n",
                this.addressId, this.userId, this.fullAddress);

// Divider
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");


        System.out.printf("┃" + YELLOW + " %-20s" + PURPLE + "┃" + YELLOW + " %-48s" + PURPLE + "┃\n",
                "Phone", "Receive Name");
        System.out.printf("┃ %-19s " + PURPLE + "┃ %-47s " + PURPLE + "┃\n",
                this.phone, this.receiveName);

        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);


    }
}
