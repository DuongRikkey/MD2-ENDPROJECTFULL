package presentation.admin.productMenu;

import business.entity.Product;
import business.features.IGenericDesign;
import business.features.impl.CategoryImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.WishListImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.InputMethod;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
   public static IGenericDesign productImpl=new ProductImpl();
    public void showMenuProduct(Scanner scanner) {


        boolean isLoop = true;
        do {
            // Define ANSI escape codes for purple borders and yellow text
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          PRODUCT MENU                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị toàn bộ sản phẩm                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thêm sản phẩm mới                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Chỉnh sửa sản phẩm                               " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Xóa sản phẩm                                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Tìm kiếm sản phẩm qua ID                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Sắp xếp sản phẩm theo giá                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   7. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");

            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    showAllProduct();
                    break;
                }
                case 2:{
                    addNewProduct(scanner);
                    break;
                }
                case 3:{
                    editProduct(scanner);
                    break;
                }
                case 4:{
                    deleteProduct(scanner);
                    break;
                }
                case 5:{
                    searchProductByID(scanner);
                    break;
                }
                case 6:{
                    sortProductbyPrice(scanner);
                    break;
                }
                case 7:{
                    isLoop = false;
                    break;
                }

                default:
                    System.err.println("Bạn phải nhập từ 1->7");
            }



        }while (isLoop);

    }

    private void sortProductbyPrice(Scanner scanner) {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách sản phẩm trống");
            return;
        }
        List<Product> productList =  ProductImpl.productList.stream().sorted(Comparator.comparingDouble(Product::getUnitPrice).reversed()).toList();;
       int Per_Page=10;
       PaginationHelper.showPaginatedList(productList, Per_Page);
    }

    private void searchProductByID(Scanner scanner) {
        System.out.println("Mời bạn nhập ID muốn tim của sản phẩm");
        int idSearch = InputMethod.getInteger();
        List<Product> matchingProduct=ProductImpl.productList.stream().filter(product -> product.getProductId()==idSearch).toList();
        if(matchingProduct.isEmpty()){
            System.err.println("Không thể tìm thấy sản phẩm");
        }else {
            matchingProduct.stream().forEach(Product::displayData);
        }
    }

    private void deleteProduct(Scanner scanner) {
        int idDelete;
        System.out.println("Mời bạn nhập sản phẩm muốn xóa");
        while (true){
            try {
                idDelete = Integer.parseInt(scanner.nextLine());
                if (idDelete <= 0) {
                    System.err.println("Bạn phải nhập số dương");
                    continue;
                }
                break;

            }catch (NullPointerException e){
                System.err.println("Bạn phải nhập số nguyên hợp lêk");
            }
        }
        int finalIdDelete = idDelete;
        boolean isExist = ProductImpl.productList.stream().anyMatch(product -> product.getProductId()==finalIdDelete);
        if (!isExist) {
            System.err.println("Không tìm thấy sản phẩm cần xóa");
        }
        boolean checkW= WishListImpl.wishLists.stream().anyMatch(wishList -> wishList.getProductWishList().getProductId()==finalIdDelete);
        if (checkW) {
            System.err.println("Không thể xóa sản phẩm");
        }else {
            ProductImpl.productList.remove(finalIdDelete);
        }
//        boolean check
////        if (isExist) {
////            productImpl.remove(finalIdDelete);
////            System.out.println("Xóa thành công");
////        }
////        else{
////            System.err.println("Không thể tìm thấy Id cần xóa"+finalIdDelete);
////        }

    }

    private void editProduct(Scanner scanner) {
        int productIdUpdate;
        while (true){
            try {System.out.println("Nhập mã sản phẩm vào vào");
                productIdUpdate = Integer.parseInt(scanner.nextLine());
                if(productIdUpdate<=0){
                    System.err.println("Bạn phải nhập số lớn hơn 0");
                    continue;
                }
                break;

            }
            catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }

        }
        int indexUpdate=productImpl.findIndexbyID(productIdUpdate);
        if(indexUpdate==-1){
            System.err.println("Không tiìm thấy ID update"+productIdUpdate);

        }else {
            Product productOld=ProductImpl.productList.get(indexUpdate);
            productOld.inputData(scanner,false);
            productImpl.addAndUpdate(productOld);
        }

    }

    private void addNewProduct(Scanner scanner) {
        if (CategoryImpl.categoryList.isEmpty()){
            System.err.println("Category đang trống không thể thêm sản phẩm");
            return;
        }
        int n=0;
        while (true){
            try {
                System.out.println("Mời bạn nhập số sản phẩm muốn thêm vào");
                n=Integer.parseInt(scanner.nextLine());
                if (n<=0){
                    System.err.println("Không được nhỏ hơn hoặc bằng 0 số sản phẩm muốn thêm vào");
                    continue;
                }
                break;
            }catch (NumberFormatException e){
                System.err.println("Thêm vào phải là số nguyên");
            }
        }
        for (int i=0;i<n;i++){
            System.out.println("Thêm sản phẩm thứ "+(i+1));
            Product product = new Product();
            product.inputData(scanner,true);
            productImpl.addAndUpdate(product);
        }
    }

    private void showAllProduct() {
        List<Product> productList = ProductImpl.productList;
        int itemsPerPage = 3; // Số mục mỗi trang
        PaginationHelper.showPaginatedList(productList, itemsPerPage);
    }

    }

