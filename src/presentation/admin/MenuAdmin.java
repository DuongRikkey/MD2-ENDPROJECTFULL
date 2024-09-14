package presentation.admin;

import business.utils.InputMethod;
import presentation.admin.category.CategoryMenu;
import presentation.admin.odermenu.OrderMenu;
import presentation.admin.productMenu.ProductMenu;
import presentation.admin.statistícsMenu.StatisticsMenu;
import presentation.admin.userManagerMenu.userManagerMenu;

import java.text.ParseException;
import java.util.Scanner;

public class MenuAdmin {
    public static void menuAdmin(Scanner scanner)  {
        boolean isLoop=true;
        do {
            // Define ANSI escape codes for purple borders and yellow text
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                             MENU ADMIN                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               1. Hệ thống quản lý danh mục                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               2. Hệ thống quản lý sản phẩm                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               3. Hệ thống quản lý người dùng (Quản trị viên)          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               4. Hệ thống quản lý đơn hàng                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               5. Thống kê                                             " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               6. Đăng xuất                                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    CategoryMenu categoryMenu = new CategoryMenu();
                    categoryMenu.showMenuCategory(scanner);
                    break;
                }
                case 2:{
                    ProductMenu productMenu = new ProductMenu();
                    productMenu.showMenuProduct(scanner);
                    break;
                }
                case 3:{
                    userManagerMenu userMenu=new userManagerMenu();
                    userMenu.showUserMenu(scanner);
                    break;
                }
                case 4:{
                    OrderMenu orderMenu=new OrderMenu();
                    orderMenu.showOrderMenu(scanner);
                    break;
                }
                case 5:{
                    StatisticsMenu statisticsMenu=new StatisticsMenu();
                    statisticsMenu.showStatisticsMenu(scanner);
                    break;
                }
                case 6:{ isLoop=false;
                    break;}
                default:
                    System.err.println("Nhập từ 1 đến 6");
            }

        }while (isLoop);


    }
}
