package business.features.impl.productImpl.User;

import business.entity.Category;
import business.entity.OrderDetails;
import business.entity.Product;
import business.features.impl.CategoryImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.orderDetails.OrderDetailsImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.Colors;
import business.utils.InputMethod;
import presentation.admin.productMenu.ProductMenu;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductFeaturesForUser {
    public static void displayAllProductsUser() {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        if (ProductImpl.productList != null && !ProductImpl.productList.isEmpty()) {
//            System.out.println(Colors.CYAN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
//            System.out.println(Colors.CYAN + "┃                           ** LIST PRODUCTS **                        ┃" + Colors.RESET);
//            System.out.println(Colors.CYAN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);
            int PER_PAGE=3;
            List<Product> productList =ProductImpl.productList.stream()
                    .filter(product ->  product.getCategory().isStatus()).toList(); // Ensure category is not null
            PaginationHelper.showPaginatedList(productList, PER_PAGE);
            // Display data of products that belong to active categories
        } else {
            System.out.println("Không có sản phẩm nào");
        }
        System.out.println(Colors.GREEN +"Có " +ProductImpl.productList.stream().filter(product ->  product.getCategory().isStatus()).count() +" sản phẩm" +Colors.RESET);

    }

    public static void showNewstProducts() {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        System.out.println(Colors.GREEN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
        System.out.println(Colors.GREEN + "┃                    ******** PRODUCTS NEWEST *********                 ┃" + Colors.RESET);
        System.out.println(Colors.GREEN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

        ProductImpl.productList.stream().filter(product ->  product.getCategory().isStatus()).
                sorted(Comparator.comparing(Product::getCreatedAt).reversed()).limit(2).forEach(Product::displayDataForUser);
    }



    public static void displayBestSelling() {
        if (ProductImpl.productList.isEmpty()) {
            System.err.println("Danh sách trống");
            return;
        }

        List<OrderDetails> orderDetailsList = OrderDetailsImpl.orderDetailsList;
        Map<Product, Integer> productIntegerMap = new HashMap<>();

// Iterate through order details to accumulate quantities for each product
        for (OrderDetails orderDetails : orderDetailsList) {
            // Find the product associated with the current order detail
            Product product = ProductImpl.productList.stream()
                    .filter(p -> p.getProductId() == orderDetails.getProductId())  // Assuming getProductId() in OrderDetails
                    .findFirst()
                    .orElse(null);

            if (product != null) {
                int quantity = orderDetails.getOrder_quantity();
                productIntegerMap.merge(product, quantity, Integer::sum);
            }
        }

// Get the top 3 best-selling products
        List<Product> bestSellingProducts = productIntegerMap.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (bestSellingProducts.isEmpty()) {
            System.out.println("Không có sản phẩm bán chạy.");
            return;
        }

        System.out.println(Colors.CYAN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
        System.out.println(Colors.CYAN + "┃                  ******** BEST SELLING PRODUCTS ********              ┃" + Colors.RESET);
        System.out.println(Colors.CYAN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

// Display the list of best-selling products
        bestSellingProducts.forEach(Product::displayDataForUser);






    }

    public static void showDetailsProductByID() {
        if (ProductImpl.productList.isEmpty()){
            System.err.println("Danh sách trống");
            return;
        }
        System.out.println("Mời bạn nhập ID tìm kiếm");
        int idProduct= InputMethod.getInteger();
        List<Product> matchingProduct =ProductImpl.productList.stream().filter(product -> product.getProductId()==idProduct).toList();
        if (matchingProduct.isEmpty()){
            System.err.println("Không tìm thấy ID"+idProduct);
        }else {
            matchingProduct.forEach(Product::displayDataForUser);
        }
    }


        public static void searchProductByCategory() {
//            CategoryImpl.categoryList.stream().forEach(category -> {
//                System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
//                System.out.println("┃                                                                           ┃");
//                System.out.println("┃" + Colors.YELLOW + "Danh mục: " + category.getCategoryName() );
//                System.out.println("┃                                                                           ┃");
//                System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
//
//                ProductImpl.productList.stream()
//                        .filter(product -> product.getCategory().getCategoryId() == category.getCategoryId())
//                        .forEach(product -> {
//                            System.out.println("┃" + Colors.GREEN+ "Sản phẩm: " + product.getProductName() );
//                        });
//
//                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);
//                System.out.println(Colors.BLUE+ "Danh mục: " +  category.getCategoryName()+ " có "+ProductImpl.productList.stream().filter(product -> product.getCategory().getCategoryId()== category.getCategoryId()).count()+ " sản phẩm" );
//            });
            if (ProductImpl.productList.isEmpty()) {
                System.err.println("Danh sách trống");
                return;
            }
            System.out.println("Mời bạn nhập tên category:");
            String nameCate = InputMethod.getString().toLowerCase();

            List<Product> matchingProduct=ProductImpl.productList.stream().filter(product -> product.getCategory().getCategoryName().toLowerCase().contains(nameCate)).toList();
            int Per_Page=10;
            PaginationHelper.showPaginatedList(matchingProduct, Per_Page);
            if (matchingProduct.isEmpty()) {
                System.err.println("Không tìm thấy sản phẩm với từ khóa: " + nameCate);
            } else {
                matchingProduct.forEach(Product::displayDataForUser);
            }

        }
    public static void searchProductByNameORDes() {
        if (ProductImpl.productList.isEmpty()) {
            System.err.println("Danh sách trống");
            return;
        }

        System.out.println("Nhập tên sản phẩm hoặc tên mô tả:");
        String nameORDes = InputMethod.getString().toLowerCase(); // Xóa khoảng trắng và chuyển chuỗi về chữ thường

        List<Product> matchingProduct = ProductImpl.productList.stream()
                .filter(product -> product.getDescription().toLowerCase().contains(nameORDes)
                        || product.getProductName().toLowerCase().contains(nameORDes))
                .toList();

        if (matchingProduct.isEmpty()) {
            System.err.println("Không tìm thấy sản phẩm với từ khóa: " + nameORDes);
        } else {
            matchingProduct.forEach(Product::displayDataForUser);
        }
    }

}



