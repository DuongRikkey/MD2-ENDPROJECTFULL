package presentation.user.userInformation;

import business.entity.Address;
import business.features.impl.userImpl.user.UserOfUseImpl;
import business.utils.InputMethod;
import presentation.user.addressForUserMenu.AddressForUserMenu;

import java.util.Scanner;

public class UserInformationMenu {
    public static void informationMenuForUSER(Scanner scanner) {
        boolean isLoop = true;
        do {
            // Define ANSI escape codes for purple borders and yellow text
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          USER INFORMATION                             " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị thông tin người dùng                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Sửa thông tin người dùng                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Địa chỉ thông tin người dùng                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Đổi mật khẩu                                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Nạp tiền                                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    UserOfUseImpl.showInformationUser();
                    break;
                }
                case 2:{
                    UserOfUseImpl.editInformationUser(scanner);
                    break;
                }
                case 3:{
                    AddressForUserMenu.addressOfUser(scanner);
                    break;
                }
                case 4:{
                    UserOfUseImpl.changePassword(scanner);
                    break;
                }
                case 5:{
                    UserOfUseImpl.rechargeMoney(scanner);
                    break;
                }
                case 6:{
                    isLoop = false;
                    break;
                }
            }



        }while (isLoop);

    }

    }

