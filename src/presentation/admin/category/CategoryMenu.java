package presentation.admin.category;

import business.entity.Category;
import business.features.IGenericDesign;
import business.features.impl.CategoryImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.UsersImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.*;

import java.util.List;
import java.util.Scanner;

public class CategoryMenu {
    public static IGenericDesign categoryImp = new CategoryImpl();
    public void showMenuCategory(Scanner scanner) {
        boolean isLoop = true;
        do {
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          CATEGORY MENU                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị toàn bộ category                        " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thêm mới category                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Chỉnh sửa category                               " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Xóa category                                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Tìm kiếm category qua ID                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Lựa chọn của bạn: ");




            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    showAllCategory();
                    break;
                }
                case 2:{
                    addCategory(scanner);
                    break;
                }
                case 3:{
                    editCategory(scanner);
                    break;
                }
                case 4:{
                    deleteCategory(scanner);
                    break;
                }
                case 5:{
                    searchCategoryByID(scanner);
                    break;
                }
                case 6:{
                    isLoop=false;
                    break;
                }
                default:
                    System.err.println(ShopTextMessege.ERROR_ALERT);
            }



        }while (isLoop);


    }

    private void deleteCategory(Scanner scanner) {
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
        boolean isExist = CategoryImpl.categoryList.stream().anyMatch(category -> category.getCategoryId()==finalIdDelete);
        if (!isExist){
            System.err.println("Không tìm thấy Cate"+finalIdDelete);
            return;
        }
        boolean hasProduct = ProductImpl.productList.stream().anyMatch(product -> product.getCategory().getCategoryId()==finalIdDelete);
        if (hasProduct){
            System.err.println("Không thể xóa danh mục vì đã có sản phẩm sử dụng danh mục");
        }
        else {
            categoryImp.remove(finalIdDelete);
            System.out.println("Xóa thành công");
        }
        IOFILE.writeListToFile(CategoryImpl.categoryList, ShopConstanst.CATEGORY_PATH);
    }

    private void searchCategoryByID(Scanner scanner) {
        System.out.println("Mời bạn nhập ID của danh  mục");
        int idSearch = InputMethod.getInteger();
        List<Category> matchingCate=CategoryImpl.categoryList.stream().filter(category -> category.getCategoryId()==idSearch).toList();
        if (matchingCate.isEmpty()){
            System.err.println("Không tìm thấy Cate thông qua id "+idSearch);
        }else {
            matchingCate.stream().forEach(Category::displayData);
        }
//        IOFILE.writeListToFile(CategoryImpl.categoryList, ShopConstanst.CATEGORY_PATH);

    }

    private void editCategory(Scanner scanner) {
        int cateIdUpdate;
        while (true){
            try {System.out.println("Nhập mã catalog vào");
                cateIdUpdate = Integer.parseInt(scanner.nextLine());
                if(cateIdUpdate<=0){
                    System.err.println("Bạn phải nhập số lớn hơn 0");
                    continue;
                }
                break;

            }
            catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }

        }
        int indexUpdate=categoryImp.findIndexbyID(cateIdUpdate);
        if(indexUpdate==-1){
            System.err.println("Khong ton tai ID "+cateIdUpdate);

        }else {
            Category categoryOld=CategoryImpl.categoryList.get(indexUpdate);
            categoryOld.inputUpdate(scanner);
            categoryImp.addAndUpdate(categoryOld);
            System.out.println(Colors.YELLOW+"Sửa thành công"+Colors.BLUE);
        }




    }

    private void addCategory(Scanner scanner) {
        System.out.println("Mời bạn nhập số lượng muốn thêm vào");
        int n=InputMethod.getInteger();
        for (int i=0;i<n;i++) {
            System.out.println("Thêm danh mục thứ "+(i+1));
            Category category=new Category();
            category.inputData(scanner,true);
            categoryImp.addAndUpdate(category);
        }



    }

    private void showAllCategory() {
        int itemPerPage=3;
        List<Category> categoryList=CategoryImpl.categoryList;
        PaginationHelper.showPaginatedList(categoryList,itemPerPage);
        }

    }

