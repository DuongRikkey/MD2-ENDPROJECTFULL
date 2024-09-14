package business.features.impl.PaginationHelper;

import business.features.IODATA;
import business.utils.Colors;
import business.utils.InputMethod;

import java.util.List;

public class PaginationHelper {

    public static <T extends IODATA> void showPaginatedList(List<T> list, int itemsPerPage) {
        if (list == null || list.isEmpty()) {
            System.err.println("Danh sách trống");
            return;
        }

        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        int currentPage = 1;

        while (true) {


            int start = (currentPage - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, totalItems);

            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "                            LIST-INFORMATION                           " +Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            for (int i = start; i < end; i++) {
                list.get(i).displayData();
            }
            System.out.println("Trang " + currentPage + " / " + totalPages);
            System.out.println("Nhập lệnh:");
            // Sắp xếp các tùy chọn phân trang theo cấu trúc yêu cầu
            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃                             ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃        1.Trang đầu tiên   ┃       2.Trang trước         ┃       3.Trang sau           ┃       4.Trang cuối cùng     ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃                             ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃                             ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃       5. Tìm kiếm trang     ┃       6.Thoát               ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┃                           ┃                             ┃                             ┃                             ┃" + Colors.RESET);
            System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

            System.out.println("Mời bạn lựa chọn");


            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    currentPage=1;
                    break;
                }
                case 2:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Đây là trang đầu tiên.");
                    }
                    break;

                case 3:

                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("Đây là trang cuối cùng.");
                    }
                    break;
                case 4:{
                    currentPage=totalPages;
                    break;
                }
                case 5:
                    System.out.println("Nhập số trang bạn muốn đến:");
                    int inputPage = InputMethod.getInteger();
                    if (inputPage >= 1 && inputPage <= totalPages) {
                        currentPage = inputPage;
                    } else {
                        System.out.println("Số trang không hợp lệ. Vui lòng nhập lại.");
                    }
                    break;
                case 6:
                    return; // Thoát khỏi phân trang
                default:
                    System.out.println("Lựa chọn không hợp lệ.nhập từ 1->6");
            }
        }
    }
}

