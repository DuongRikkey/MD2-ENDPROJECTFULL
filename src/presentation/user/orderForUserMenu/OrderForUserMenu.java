package presentation.user.orderForUserMenu;

import business.features.impl.oderImpl.OrderFeatures;
import business.utils.InputMethod;

import java.text.ParseException;
import java.util.Scanner;

public class OrderForUserMenu {
    public static void showOrderMenuForUser(Scanner scanner) {
        final String PURPLE = "\033[35m";  // Purple for borders
        final String YELLOW = "\033[33m"; // Yellow for text
        final String RESET = "\033[0m";   // Reset to default color
        boolean isLoop=true;
        do {
            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          MENU ORDER                                   " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị lịch sử đơn hàng                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Xem chi tiết đơn hàng                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Hiển thị đơn hàng theo trạng thái                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Hủy đơn hàng                                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Tìm kiếm đơn hàng theo ngày A -> B               " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Tìm kiếm đơn hàng theo trạng thái                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   7. Quay lại Menu Chính                              " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Nhập lựa chọn của bạn: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    OrderFeatures.showAllDrder(false);
                    break;
                }
                case 2:{
                   OrderFeatures.showDetailOrder(false);
                   break;
                }
                case 3:{
                    OrderFeatures.showOrderByStatus(false);
                    break;
                }
                case 4:{
                    OrderFeatures.changeByStatus();
                    break;
                }
                case 5:{
                    try {
                        OrderFeatures.searchOrderByDays(scanner,false);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 6:{
                    OrderFeatures.searchOrderByStatus(scanner,false);
                    break;
                }
                case 7:{
                    return;
                }
                default:
                    System.err.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");

            }

        } while (true);
    }
}
