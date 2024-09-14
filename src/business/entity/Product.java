package business.entity;

import business.features.IODATA;
import business.features.impl.CategoryImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.InputMethod;
import business.utils.ShopTextMessege;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Product implements Serializable, IODATA<Product,String> {
    private int productId,stockQuantity,selled;
    private String productName,sku,image,description;
    private double unitPrice;
    private Category category;
    private Date createdAt,updatedAt;

    public Product() {
    }

    public Product(int productId, int stockQuantity, int selled, String productName, String sku, String image, String description, double unitPrice, Category category, Date createdAt, Date updatedAt) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
        this.selled = selled;
        this.productName = productName;
        this.sku = sku;
        this.image = image;
        this.description = description;
        this.unitPrice = unitPrice;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getSelled() {
        return selled;
    }

    public void setSelled(int selled) {
        this.selled = selled;
    }

    public void showMenuProduct(Scanner scanner) {

    }

    @Override
    public void inputData(Scanner sc, boolean isAdd) {
        if (isAdd){
            this.createdAt=new Date();
        }
        this.productName=inputProductName(sc);
        this.stockQuantity=inputQuantity(sc);
        this.description=inputDes(sc);
        this.unitPrice=inputPrice(sc);
        this.category=inputCategory(sc);
        this.sku=inputSku(sc);
        this.updatedAt=new Date();
    }

    private String inputSku(Scanner sc) {
        UUID coderandom = UUID.randomUUID();
//        Đây là một phương thức tĩnh trong lớp UUID, sử dụng để tạo ra một UUID ngẫu nhiên
        return coderandom.toString();
    }

    private Category inputCategory(Scanner sc) {
        CategoryImpl.categoryList.forEach(category -> System.out.printf("[Id:%d, Name:%s]\n",category.getCategoryId(),category.getCategoryName()));
        System.out.println("Lựa chọn ID category");
        do {
            int idCate=InputMethod.getInteger();
            Optional<Category> optionalCategory=CategoryImpl.categoryList.stream().filter(product->product.getCategoryId()==idCate).findFirst();
            if (optionalCategory.isPresent()) {
                return optionalCategory.get();
            }



        }while (true);

    }

    private double inputPrice(Scanner sc) {
        do {
            System.out.println("Mời bạn nhập giá sản phẩm");
            double productQuantity=InputMethod.getDouble();
            if (productQuantity>0) {
                return productQuantity;
            }else {
                System.err.println(ShopTextMessege.ERROR_Number);
            }


        }while (true);
    }

    private String inputDes(Scanner sc) {
        //Check lại
        System.out.println("Mời bạn nhập mô tả sản phẩm");
        return InputMethod.getString();
    }

    private int inputQuantity(Scanner sc) {
        System.out.println("Mời bạn nhập số lượng sản phẩm muốn thêm vào");
        do {
            int quantity = InputMethod.getInteger();
            if (quantity <= 0) {
                System.err.println("Số lượng sản phẩm phải lớn hơn 0");
            }else {
                return quantity;
            }

        }while(true);


    }

    private String inputProductName(Scanner sc) {
        System.out.println("Mời bạn nhập tên ");
        do {

            String productName = InputMethod.getString();
            boolean isExist = (ProductImpl.productList.stream().anyMatch(product -> product.getProductName().equals(productName)));
            if (isExist) {
                System.err.println(ShopTextMessege.ERROR_ProdcutName_Exist);
            }else{
                return productName;
            }

        }while (true);
    }

//    @Override
//    public void displayData() {
//        // Locale for formatting currency
//        Locale locale = new Locale("vi", "VN");
//        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        // Format and display product data
//        System.out.printf(
//                "[Id: %5d |Name: %10s |Price: %10s |StockQuantity: %5d |Selled: %5d |Creat: %15s |Update; %15s |Sku: %10s |Cate: %s |Des: %s]\n",
//                this.productId,
//                this.productName,
//                formatter.format(this.unitPrice),
//                this.stockQuantity,
//                this.selled,
//                this.createdAt != null ? dateFormat.format(this.createdAt) : "N/A",
//                this.updatedAt != null ? dateFormat.format(this.updatedAt) : "N/A",
//                this.sku,
//                this.category != null ? this.category.getCategoryName() : "N/A",
//                this.description
//        );
    final String PURPLE = "\033[35m";  // Purple for borders
    final String CYAN = "\033[36m";    // Cyan for headers
    final String YELLOW = "\033[33m";  // Yellow for text
    final String GREEN = "\033[32m";   // Green for active status
    final String RED = "\033[31m";     // Red for inactive status
    final String RESET = "\033[0m";
    @Override
    public void displayData() {
        // Locale for formatting currency in VND
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + CYAN + "                           PRODUCT DETAILS                             " + PURPLE +  "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-32s" + PURPLE + "┃" + YELLOW + " %-23s" + PURPLE + "┃\n",
                "ID", "Name", "Price");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃ %-10d " + PURPLE + "┃ %-31s " + PURPLE + "┃ %-22s " + PURPLE + "┃\n",
                this.productId, this.productName, formatter.format(this.unitPrice));
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-14s" + PURPLE + "┃" + YELLOW + " %-41s" + PURPLE + "┃\n",
                "StockQty", "Selled", "SKU");
        System.out.printf("┃ %-10d " + PURPLE + "┃ %-13d " + PURPLE + "┃ %-40s " + PURPLE + "┃\n",
                this.stockQuantity, this.selled, this.sku);
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-16s" + PURPLE + "┃" + YELLOW + " %-26s" + PURPLE + "┃" + YELLOW + " %-24s" + PURPLE + "┃\n",
                "CreatedAt", "UpdatedAt", "Category");
        System.out.printf("┃ %-15s " + PURPLE + "┃ %-25s " + PURPLE + "┃ %-23s " + PURPLE + "┃\n",
                this.createdAt != null ? dateFormat.format(this.createdAt) : "N/A",
                this.updatedAt != null ? dateFormat.format(this.updatedAt) : "N/A",
                this.category != null ? this.category.getCategoryName() : "N/A");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃                          " + CYAN + " Description                                 " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃                         %-45s " + PURPLE + "┃\n", this.description);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }
    public void displayDataForUser() {
        // Locale for formatting currency in VND
        Locale localeVn = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeVn);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + PURPLE + "                           PRODUCT DETAILS                             " + PURPLE +  "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Header row
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-32s" + PURPLE + "┃" + YELLOW + " %-23s" + PURPLE + "┃\n",
                "ID", "Name", "Price");

        // Product data row
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃ %-10d " + PURPLE + "┃ %-31s " + PURPLE + "┃ %-22s " + PURPLE + "┃\n",
                this.productId, this.productName, currencyFormat.format(this.unitPrice));

        // StockQty and Category row
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-57s" + PURPLE + "┃\n",
                "Quantity", "CategoryName");
        System.out.printf("┃ %-10d " + PURPLE + "┃ %-56s " + PURPLE + "┃\n",
                this.stockQuantity, this.category != null ? this.category.getCategoryName() : "N/A");

        // Description row
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃                          " + PURPLE + " Description                                 " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃                         %-45s " + PURPLE + "┃\n",
                this.description != null ? this.description : "No description available");

        // Closing the table
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }




}

