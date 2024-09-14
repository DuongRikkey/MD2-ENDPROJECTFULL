package presentation.user;

import business.features.impl.productImpl.User.ProductFeaturesForUser;
import business.utils.Colors;
import business.utils.InputMethod;


import java.awt.*;

public class GuestView {

    public static void showGuestView() {
        boolean isLoop=true;

        do {
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          PRODUCT MENU                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               1. Hiển thị toàn bộ sản phẩm                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               2. Sản phẩm mới                                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               3. Sản phẩm bán chạy                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               4. Hiển thị sản phẩm theo danh mục                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               5. Hiển thị chi tiết sản phẩm theo ID                   " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "               6. Tìm kiếm sản phẩm theo tên hoặc mô tả                " + PURPLE + "┃");
            System.out.println("┃                                                 '/                      ┃");
            System.out.println("┃" + YELLOW + "               7. Trở lại                                              " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");
            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    ProductFeaturesForUser.displayAllProductsUser();
                    break;
                }
                case 2:{
                    ProductFeaturesForUser.showNewstProducts();
                    break;
                }
                case 3:{
                    ProductFeaturesForUser.displayBestSelling();
                    break;
                }
                case 4:{
                    ProductFeaturesForUser.searchProductByCategory();
                    break;
                }
                case 5:{
                    ProductFeaturesForUser.showDetailsProductByID();
                    break;
                }
                case 6:{
                    ProductFeaturesForUser.searchProductByNameORDes();
                    break;
                }
                case 7:{
                    isLoop=false;
                    break;
                }
                default:
                    System.err.println("Lựa chọn từ 1 tới 7");

            }


        } while (isLoop);
    }
}
