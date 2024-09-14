package presentation.admin.odermenu;

import business.entity.Order;
import business.features.IGenericDesign;
import business.features.impl.oderImpl.OrderFeatures;
import business.features.impl.oderImpl.OrderImpl;
import business.utils.InputMethod;

import java.text.ParseException;
import java.util.Scanner;

public class OrderMenu {
    public static IGenericDesign<Order,Integer> orderImpl = new OrderImpl();
    public void showOrderMenu(Scanner scanner) {
        final String PURPLE = "\033[35m";  // Purple for borders
        final String YELLOW = "\033[33m";  // Yellow for text
        final String RESET = "\033[0m";    // Reset to default color
        boolean isLoop = true;
        do {
            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                        ORDER MANAGEMENT MENU                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị lịch sử đơn hàng                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Xem chi tiết đơn hàng                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Tìm kiếm theo trạng thái đơn hàng                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Tìm kiếm đơn hàng theo ngày a -> b               " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Thay đổi trạng thái đơn hàng                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Hiển thị đơn hàng theo trạng thái đơn            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   7. Quay trở lại                                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    OrderFeatures.showAllDrder(true);
                    break;
                }
                case 2:{
                    OrderFeatures.showDetailOrder(true);
                    break;
                }
                case 3:{
                    OrderFeatures.searchOrderByStatus(scanner,true);
                    break;
                }
                case 4:{

                    try {
                        OrderFeatures.searchOrderByDays(scanner,true);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 5:{
                    OrderFeatures.changeOrderStatus(scanner);
                    break;
                }
                case 6:{
                    OrderFeatures.showOrderByStatus(true);
                }
                case 7:{
                    isLoop = false;
                    break;
                }
                default:
                    System.err.println("Bạn phải nhập từ 1->7");
        }
      }
        while (isLoop);
     }
}
