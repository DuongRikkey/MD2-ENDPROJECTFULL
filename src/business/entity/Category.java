package business.entity;

import business.features.IODATA;
import business.features.impl.CategoryImpl;
import business.utils.ShopTextMessege;

import java.io.Serializable;
import java.util.Scanner;

public class Category implements Serializable, IODATA<Category,String> {
    private int categoryId;
    private String categoryName,descriptions;
    private boolean status;

    public Category() {
    }

    public Category(int categoryId, String categoryName, String descriptions, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.descriptions = descriptions;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }




    private boolean inputStatus(Scanner scanner) {
        System.out.println("Mời bạn nhập True-Active|False-InActive");
        do {
            String status = scanner.nextLine();
            if (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(status);
            }else {
                System.err.println(ShopTextMessege.ERROR_ALERT);
            }

        }while(true);
    }

    private String inputDes(Scanner scanner) {
        System.out.println("Mời bạn nhập mô tả danh mục");
        do {
            String des=scanner.nextLine();
            if (des.isBlank()){
                System.err.println(ShopTextMessege.Empty_Erorr);
            }else {
                return des;
            }

        }while(true);
    }

    private String inputCateName(Scanner scanner) {
        System.out.println("Mời bạn nhập tên danh mục");
        do {
            String cateName = scanner.nextLine();
            if (cateName.isBlank()) {
                System.err.println(ShopTextMessege.Empty_Erorr);
            }else {
                boolean isExist= CategoryImpl.categoryList.stream().anyMatch(category -> category.getCategoryName().equals(cateName));
                if (isExist) {
                    System.err.println(ShopTextMessege.ERROR_Category_Exist);
                }
                else {
                    return cateName;
                }
            }
        }while(true);
    }
    @Override
    public void inputData(Scanner scanner, boolean isAdd) {
//        if(isAdd){
//            List<Category> categories=null;
//        }
        this.categoryName=inputCateName(scanner);
        this.descriptions=inputDes(scanner);
        this.status=inputStatus(scanner);
    }
    public void inputUpdate(Scanner scanner ) {
//        if(isAdd){
//            List<Category> categories=null;
//        }
        this.categoryName=inputCateName(scanner);
        this.descriptions=inputDes(scanner);
        this.status=inputStatus(scanner);
    }


    final String PURPLE = "\033[35m";  // Purple for borders
    final String CYAN = "\033[36m";    // Cyan for headers
    final String YELLOW = "\033[33m";  // Yellow for text
    final String GREEN = "\033[32m";   // Green for active status
    final String RED = "\033[31m";     // Red for inactive status
    final String RESET = "\033[0m";    // Reset to default color

    @Override
    public void displayData() {
        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + CYAN + "                         CATEGORY DETAILS                              " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-31s" + PURPLE + "┃" + YELLOW + " %-24s" + PURPLE +     "┃\n",
                "ID", "Name", "Status");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃ %-10d " + PURPLE + "┃ %-30s " + PURPLE + "┃ %-33s " + PURPLE + "┃\n",
                this.categoryId, this.categoryName, this.status ? GREEN + "Active" + PURPLE : RED + "Inactive" + PURPLE);
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃                          " + CYAN + " Description                                 " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃                            %-42s " + PURPLE + "┃\n", this.descriptions);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }

}
