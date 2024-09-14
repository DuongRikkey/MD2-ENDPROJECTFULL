package presentation.user.forgotPassword;

import business.entity.Users;
import business.features.impl.UsersImpl;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.Random;
import java.util.Scanner;

public class ForgotPassword {
    public  static UsersImpl usersImpl = new UsersImpl();
    public static String generateRandomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String allCharacters = upperCaseLetters + digits;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Đảm bảo ít nhất một chữ cái in hoa
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));

        // Đảm bảo ít nhất một chữ số
        password.append(digits.charAt(random.nextInt(digits.length())));

        // Thêm các ký tự ngẫu nhiên khác để đạt tối thiểu 6 ký tự
        for (int i = 2; i < 6; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        return password.toString();
    }
    public static void showForgotPassword(Scanner sc) {
        System.out.print("Nhập tài khoản của bạn: ");
        String username = sc.nextLine();
        System.out.print("Nhập email của bạn: ");
        String email = sc.nextLine();

        // Tìm người dùng theo tài khoản và email
        Users user = UsersImpl.findByUsernameAndEmail(username, email);



        if (user != null) {
            // Hiển thị mật khẩu trên console
            String newPassword = generateRandomPassword();
            user.setPassword(newPassword);
            System.out.println("Mật khẩu của bạn đã được đặt lại là: " + user.getPassword());

            // Lưu cập nhật người dùng vào tệp (nếu cần thiết)
            IOFILE.writeListToFile(UsersImpl.usersList, ShopConstanst.USER_PATH);
        } else {
            System.err.println("Tài khoản hoặc email không đúng.");
        }
    }
}
