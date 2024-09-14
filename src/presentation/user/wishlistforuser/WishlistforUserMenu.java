package presentation.user.wishlistforuser;

import business.entity.Product;
import business.entity.WishList;
import business.features.IGenericDesign;
import business.features.impl.WishListImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.Colors;
import business.utils.InputMethod;
import presentation.admin.productMenu.ProductMenu;
import presentation.run.Main;

import java.util.Scanner;

public class WishlistforUserMenu {
    public static IGenericDesign wishListImopl= new WishListImpl();
    public static void wishListForUserMenu(Scanner scanner) {
        boolean isLoop=true;
        do {
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                           WISHLIST                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị danh sách yêu thích                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thêm mới sản phẩm yêu thích                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Xóa sản phẩm yêu thích                           " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Quay lại                                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Lựa chọn của bạn: ");
            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    showAllWishList(scanner);
                    break;
                }
                case 2:{
                    addNewWishList(scanner);
                    break;
                }
                case 3:{
                    deleteWishList(scanner);
                    break;
                }
                case 4:{
                    isLoop=false;
                    break;
                }
                default:
                    System.err.println("Lựa chọn từ 1-> 4");
                    System.out.println();
            }


        }while(isLoop);
    }

    private static void deleteWishList(Scanner scanner) {
        int idDelete;
        while (true){
            try {
                System.out.println("Mời bạn nhập ID để xóa");
                idDelete = Integer.parseInt(scanner.nextLine());
                if (idDelete <= 0){
                    System.err.println("Bạn phải nhập số dương");
                    continue;
                }
                break;


            }catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên hợp lệ");
            }
        }
        int finalIdDelete = idDelete;
        boolean isExit=WishListImpl.wishLists.stream().anyMatch(wishList -> wishList.getWishListID()==finalIdDelete);
        if (isExit){
            wishListImopl.remove(finalIdDelete);
            System.out.println(Colors.YELLOW+"Đã xóa danh sách yêu thích thành công"+Colors.RESET);
        }else {
            System.err.println("Không tìm thấy ID cần xóa "+finalIdDelete);
        }
    }

    private static void addNewWishList(Scanner scanner) {
        ProductImpl.productList.stream().forEach(Product::displayDataForUser);
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách sản phẩm trống");
            return;
        }
        int productIDWL;
        while (true){
            try {
                System.out.println("Mời bạn nhập mã sản phẩm yêu thích theo ID" );
                productIDWL = Integer.parseInt(scanner.nextLine());
                if (productIDWL<=0){
                    System.err.println("Bạn phải nhập là số dương");
                    continue;
                }
                break;
            }catch (NumberFormatException e){
                System.err.println("Bạn phải nhập số nguuyên");
            }
        }
        int productID=productIDWL;
        if (!ProductImpl.productList.isEmpty()){
            boolean check=WishListImpl.wishLists.stream().noneMatch(wishList -> wishList.getProductWishList().getProductId()==productID && wishList.getUserWishList().getId()==Main.userLogin.getId());
            if (check){
                WishList newWishList=new WishList();
                newWishList.inputData(ProductImpl.productList.get(ProductMenu.productImpl.findIndexbyID(productID)));
                newWishList.setWishListID(Main.userLogin.getId());
                wishListImopl.addAndUpdate(newWishList);
                System.out.println("Đã thêm thành công");
            }
            else {
                System.err.println("Sản phẩm đã bị trùng lặp tại danh sách yêu thích");
            }
        }else {
            System.err.println("Wishlist not found");
        }
    }

    private static void showAllWishList(Scanner scanner) {
        if (WishListImpl.wishLists.stream().noneMatch(wishList -> wishList.getUserWishList().getId()==Main.userLogin.getId())){
            System.err.println("Danh sách yêu thích trống\n");
            System.out.println();
            return;

        }
        System.out.println("Danh sách sản phẩm");

        WishListImpl.wishLists.stream().filter(w->w.getUserWishList().getId()== Main.userLogin.getId()).forEach(WishList::displayData);
        //Bổ sung logic add vào giỏ hàng

    }
}
