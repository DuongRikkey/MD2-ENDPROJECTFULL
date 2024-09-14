package business.features.impl.userImpl.user;

import business.entity.Users;
import business.features.IGenericDesign;
import business.features.impl.UsersImpl;
import business.utils.Colors;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;
import presentation.run.Main;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class UserOfUseImpl {
    public static final int FIFTY=50000;
    public static final int ONEHUNDRED=100000;
    public static final int TWOHUNDRED=200000;
    public static final int FIVEHUNDRED=500000;
   public static IGenericDesign usersImpl =new UsersImpl();

    public static void showInformationUser() {
        System.out.println(Colors.YELLOW+"Thông tin người dùng \n"+Colors.RESET);
        Main.userLogin.displayDataforUser();
        Users users=new Users();
        users.getPassword();

    }

    public static void editInformationUser(Scanner scanner) {
        System.out.println("Mời bạn chỉnh sửa");
        Main.userLogin.inputData(scanner,false);
        usersImpl.addAndUpdate(Main.userLogin);
        System.out.println("Chỉnh sửa thành công");
    }

    public static void changePassword(Scanner scanner) {
        System.out.println("Mời bạn nhập mật khẩu cũ:");
        String oldPassword = InputMethod.getString();

// So sánh mật khẩu cũ
        if (!oldPassword.equals(Main.userLogin.getPassword())) {
            System.err.println("Mật khẩu cũ không chính xác.");
            return;
        }

        String newPassword;
        while (true) {
            System.out.println("Nhập mật khẩu mới:");
            newPassword = InputMethod.getString();

            // Kiểm tra độ dài mật khẩu
            if (newPassword.length() < 6) {
                System.err.println("Mật khẩu phải có ít nhất 6 ký tự.");
                continue;
            }

            // Kiểm tra mật khẩu có ít nhất 1 chữ số và 1 chữ cái in hoa
            if (newPassword.matches("^(?=.*[0-9])(?=.*[A-Z]).+$")) {
                // Nhập mật khẩu xác nhận
                System.out.println("Xác nhận mật khẩu mới:");
                String confirmPassword = InputMethod.getString();

                // Kiểm tra mật khẩu xác nhận có khớp với mật khẩu mới hay không
                if (!confirmPassword.equals(newPassword)) {
                    System.err.println("Mật khẩu xác nhận không khớp. Vui lòng nhập lại.");
                } else {
                    System.out.println("Đổi mật khẩu thành công!");
                    // Đặt mật khẩu mới cho người dùng
                    Main.userLogin.setPassword(newPassword);
                    // Lưu thông tin người dùng nếu cần thiết
                    IOFILE.writeListToFile(UsersImpl.usersList, ShopConstanst.USER_PATH);
                    break;
                }
            } else {
                System.err.println("Mật khẩu phải chứa ít nhất một chữ số và một chữ cái in hoa. Ví dụ: Hello123");
            }
        }

        Main.userLogin.setPassword(newPassword);
        usersImpl.addAndUpdate(Main.userLogin);
        System.out.println("Mật khẩu đã được cập nhật thành công.");
    }

    public static void rechargeMoney(Scanner scanner) {
        final String PURPLE = "\033[35m";  // Purple for borders
        final String YELLOW = "\033[33m";  // Yellow for text
        final String RESET = "\033[0m";
        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                          DEPOSIT MONEY MENU                           " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   1. 50.000 VND                                       " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   2. 100.000 VND                                      " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   3. 200.000 VND                                      " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   4. 500.000 VND                                      " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   5. Enter custom amount                              " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + YELLOW + "                   6. Back                                             " + PURPLE + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        System.out.print("Bạn vui lòng lựa chon ");

        int choice=InputMethod.getInteger();
        Locale locale= new Locale("vi","VN");
        NumberFormat currencỳFormatter = NumberFormat.getCurrencyInstance(locale);
        switch (choice) {
            case 1:{
                Main.userLogin.setWallet(Main.userLogin.getWallet()+rechargeMoney(FIFTY));
                usersImpl.addAndUpdate(Main.userLogin);
                System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" + Colors.YELLOW + "                    Wallet changed successfully                        " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" +Colors.YELLOW+ "                    Your Wallet: " + currencỳFormatter.format(Main.userLogin.getWallet()) + "                             " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;

            } case 2:{
                Main.userLogin.setWallet(Main.userLogin.getWallet()+rechargeMoney(ONEHUNDRED));
                usersImpl.addAndUpdate(Main.userLogin);
                System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" + Colors.YELLOW + "                    Wallet changed successfully                        " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" +Colors.YELLOW+ "                    Your Wallet: " + currencỳFormatter.format(Main.userLogin.getWallet()) + "                           " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            } case 3:{
                Main.userLogin.setWallet(Main.userLogin.getWallet()+rechargeMoney(TWOHUNDRED));
                usersImpl.addAndUpdate(Main.userLogin);
                System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" + Colors.YELLOW + "                    Wallet changed successfully                        " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" +Colors.YELLOW+ "                    Your Wallet: " + currencỳFormatter.format(Main.userLogin.getWallet()) + "                           " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;

            } case 4:{
                Main.userLogin.setWallet(Main.userLogin.getWallet()+rechargeMoney(FIVEHUNDRED));
                usersImpl.addAndUpdate(Main.userLogin);
                System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" + Colors.YELLOW + "                    Wallet changed successfully                        " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" +Colors.YELLOW+ "                    Your Wallet: " + currencỳFormatter.format(Main.userLogin.getWallet()) + "                           " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;

            } case 5:{
                System.out.println(Colors.CYAN+"Mời bạn nhập số tiền tùy chọn"+Colors.RESET);
                int money=InputMethod.getInteger();
                Main.userLogin.setWallet(Main.userLogin.getWallet()+money);
                usersImpl.addAndUpdate(Main.userLogin);
                System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" + Colors.YELLOW + "                    Wallet changed successfully                        " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
                System.out.println("┃                                                                       ┃");
                System.out.println("┃" +Colors.YELLOW+ "                    Your Wallet: " + currencỳFormatter.format(Main.userLogin.getWallet()) + "                           " + PURPLE + "┃");
                System.out.println("┃                                                                       ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            }
        }


    }
    public static int rechargeMoney(int constant) {
        System.out.println(Colors.BLUE+"Nhập số lượng mệnh giá mà bạn muốn nạp "+ Colors.RESET);
        int number=InputMethod.getInteger();
        return constant*number;
    }
}
