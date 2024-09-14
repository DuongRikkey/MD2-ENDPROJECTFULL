package business.entity;

import business.features.IODATA;
import presentation.run.Main;

import java.io.Serializable;
import java.util.Scanner;

public class WishList implements Serializable, IODATA<WishList,String> {
    private int wishListID;
    private Users userWishList;
    private Product productWishList;

    public WishList() {
    }

    public WishList(Product productID, Users userID, int wishListID) {
        this.productWishList = productID;
        this.userWishList = userID;
        this.wishListID = wishListID;
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public Users getUserWishList() {
        return userWishList;
    }

    public void setUserWishList(Users userWishList) {
        this.userWishList = userWishList;
    }

    public Product getProductWishList() {
        return productWishList;
    }

    public void setProductWishList(Product productWishList) {
        this.productWishList = productWishList;
    }

    @Override
    public void inputData(Scanner sc, boolean isAdd) {
    }
    public void inputData(Product product) {
        this.userWishList= Main.userLogin;
        this.productWishList= product;
    }

    @Override
    public void displayData() {
        final String PURPLE = "\033[35m";    // Tím cho đường viền
        final String YELLOW = "\033[33m";    // Vàng cho tiêu đề
        final String RESET = "\033[0m";      // Khôi phục màu mặc định

        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + YELLOW + "                                 Wishlist Detail                                 " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-8s " + PURPLE + "┃" + YELLOW + " %-20s " + PURPLE + "┃" + YELLOW + " %-45s " + PURPLE + "┃\n",
                "Wish ID", "Username", "Product Name");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

// Dòng chứa dữ liệu
        System.out.printf("┃" + PURPLE + " %-8d " + PURPLE + "┃" + PURPLE + " %-20s " + PURPLE + "┃" + PURPLE + " %-45s " + PURPLE + "┃\n",
                this.wishListID, this.userWishList.getFullName(), this.productWishList.getProductName());

// Kết thúc bảng
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);


    }
}
