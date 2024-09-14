package presentation.user;

import presentation.user.cartForUserMenu.CartForUserMenu;
import presentation.user.orderForUserMenu.OrderForUserMenu;
import presentation.user.productForUsers.ProductForUsersMenu;
import presentation.user.userInformation.UserInformationMenu;
import presentation.user.wishlistforuser.WishlistforUserMenu;

import java.util.Scanner;

public class MenuUser {
    public void menuUser(Scanner scanner){
        System.out.println("Welcome to the Menu");
        boolean isLoop=true;
        do {
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          MENU USER                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị danh sách sản phẩm                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thông tin người dùng                             " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Quản lý giỏ hàng                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Quản lý đơn hàng                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Quản lý danh sách yêu thích                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Đăng xuất                                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Lựa chọn của bạn: ");

            int choice;
            while (true){
                try {
                    System.out.println("Mời bạn lựa chọn Menu");
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice <= 0){
                        System.err.println("Bạn phải nhập số lớn hơn không");
                    }
                    break;
                }catch (NumberFormatException e){
                    System.err.println("Không phải số nguyên");
                }
            }
            switch (choice)
            {
                case 1:
                {
                    ProductForUsersMenu.showProductMenuForUser(scanner);
                    break;
                }
                case 2:
                {
                    UserInformationMenu.informationMenuForUSER(scanner);
                    break;
                }
                case 3:
                {
                    CartForUserMenu.showCartMenuForUser(scanner);
                    break;

                }
                 case 4:
                 {
                     OrderForUserMenu.showOrderMenuForUser(scanner);
                     break;
                 }
                 case 5:{
                     WishlistforUserMenu.wishListForUserMenu(scanner);
                     break;
                 }

                case 6:
                {
                    isLoop=false;
                    break;
                }
                default:
                    System.err.println("Nhập lại từ 1 -> 3 đê: ");
            }

        }while (isLoop);
    }


}
