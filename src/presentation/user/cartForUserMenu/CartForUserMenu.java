package presentation.user.cartForUserMenu;

import business.constants.Payments;
import business.entity.*;
import business.features.IGenericDesign;
import business.features.impl.AddressImpl;
import business.features.impl.CartImpl;
import business.features.impl.CategoryImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.UsersImpl;
import business.features.impl.orderDetails.OrderDetailsImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.Colors;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;
import presentation.admin.odermenu.OrderMenu;
import presentation.admin.userManagerMenu.userManagerMenu;
import presentation.run.Main;
import presentation.user.addressForUserMenu.AddressForUserMenu;
import presentation.user.orderForUserMenu.OrderForUserMenu;
import presentation.user.userInformation.UserInformationMenu;

import java.text.NumberFormat;
import java.util.*;

public class CartForUserMenu {
    public static IGenericDesign cartImpl = new CartImpl();

    public static void showCartMenuForUser(Scanner scanner) {
        boolean isLoop = true;
        do {
            final String CYAN = "\033[36m";    // Xanh dương cho đường viền và văn bản
            final String YELLOW = "\033[33m";  // Vàng cho văn bản
            final String RESET = "\033[0m";    // Khôi phục màu mặc định

            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.PURPLE + "                    QUẢN LÝ GIỎ HÀNG CỦA NGƯỜI DÙNG                    " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               1. Hiển thị giỏ hàng                                    " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               2. Thêm vào giỏ hàng                                    " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               3. Xóa sản phẩm khỏi giỏ hàng theo ID                   " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               4. Xóa tất cả sản phẩm trong giỏ hàng                   " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               5. Thay đổi số lượng sản phẩm                           " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               6. Thanh toán                                           " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + Colors.YELLOW + "               7. Trở lại                                              " + Colors.PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print(CYAN + "Nhập lựa chọn của bạn: \n " + RESET);
            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1: {
                    showAllCart();
                    break;
                }
                case 2: {
                    addToCart(scanner);
                    break;
                }
                case 3: {
                    deleteCartByID(scanner);
                    break;
                }
                case 4: {
                    deleteAllCart(scanner);
                    break;
                }
                case 5: {
                    editQuantityCart(scanner);
                    break;
                }
                case 6: {
                    paymentOfUser(scanner);
                    break;
                }
                case 7: {
                    isLoop = false;
                    break;
                }
            }
        } while (isLoop);

    }

    private static void paymentOfUser(Scanner scanner) {
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống");
            return;
        }

        CartImpl.cartList.stream().filter(cart -> cart.getUserId() == Main.userLogin.getId()).forEach(Cart::displayData);
        double totalPrice=CartImpl.cartList.stream().
                filter(cart1 -> cart1.getUserId() == Main.userLogin.getId())
                .map(item -> item.getQuantity() * item.getProduct().getUnitPrice())
                .reduce(0.0,Double::sum);
        Locale localeVn=new Locale("vi","VN");
        NumberFormat currencyFormat=NumberFormat.getCurrencyInstance(localeVn);

        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" +Colors.YELLOW + " %-72s " +Colors.PURPLE + "┃" + Colors.YELLOW + " %-15s " + Colors.PURPLE + "┃\n", "Tổng số tiền cần thanh toán là:", currencyFormat.format(totalPrice));
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        while (true) {
            System.out.println("Lựa chọn phương thức thanh toán");
            Payments[] payments = Payments.values();
            for (int i = 0; i < payments.length; i++) {
                System.out.println((i + 1) + ". " +Colors.YELLOW+  payments[i].name() +Colors.RESET);
            }
            Payments inputPayment;
            while (true) {
                try {
                    System.out.println(Colors.PURPLE + "Mời bạn lựa chọn" + Colors.RESET);
                    inputPayment = Payments.valueOf(InputMethod.getString());
                    break;
                } catch (Exception e) {
                    System.err.println("Chọn không hợp lêk");
                }
            }
            int sum = CartImpl.cartList.stream().
                    filter(cart -> cart.getUserId() == Main.userLogin.getId()).
                    mapToInt(cart -> (int) cart.getTotalPrice()).sum();
            if (inputPayment == Payments.ONLINE) {
                if (Main.userLogin.getWallet() < sum) {
                    System.err.println("Không đủ tiền thanh toán ");
                } else {
                    payment(scanner, sum, inputPayment);
                    Main.userLogin.setWallet(Main.userLogin.getWallet() - sum);
                    userManagerMenu.usersImpl.addAndUpdate(Main.userLogin);
                }
                break;
            } else if (inputPayment==Payments.COD) {
                payment(scanner, sum, inputPayment);
                System.out.println(Colors.BLUE+"Cảm ơn bạn đã mua hàng! Sản phẩm sẽ được giao đến địa chỉ của bạn."+Colors.RESET);
                // Không thay đổi ví người dùng trong trường hợp COD
                break;
            }// trường hợp COD

        }


    }

    public static void payment(Scanner scanner, int sum, Payments inputPayment) {

        if (AddressImpl.addressList.stream().noneMatch(address -> address.getUserId()==Main.userLogin.getId())){
            System.err.println("Danh sách địa chỉ của người dùng đang không có xin vui lòng cập nhật ");
            return;
        }
        System.out.println(Colors.CYAN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                                       ┃");
        System.out.println("┃" + Colors.YELLOW + "                           DANH SÁCH ĐỊA CHỈ                           " + Colors.CYAN + "┃");
        System.out.println("┃                                                                       ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

        AddressImpl.addressList.stream().filter(a -> a.getUserId() == Main.userLogin.getId()).forEach(Address::displayData);
        while (true) {
            System.out.println("Nhập ID Address mà bạn muốn checkout");
            int idAddress ;
            while (true) {
                idAddress = InputMethod.getInteger();
                if (idAddress <= 0 ){
                    System.err.println("ID lựa chọn phải lớn hơn không"+idAddress);
                }else {
                    break;
                }
            }
            if (userManagerMenu.usersImpl.findIndexbyID(idAddress) != -1) {
                Order newOrder = new Order();
                newOrder.inputDataOrder(scanner, sum, AddressImpl.addressList.get(AddressForUserMenu.addressImpl.findIndexbyID(idAddress)));
                newOrder.setPayments(inputPayment);
                OrderMenu.orderImpl.addAndUpdate(newOrder);
                List<Cart> cartsDelete = new ArrayList<>();
                for (Cart cart : CartImpl.cartList) {
                    if (cart.getUserId() == Main.userLogin.getId()) {
                        OrderDetails orderDetails = new OrderDetails();
                        orderDetails.setOrderId(newOrder.getOrderId());
                        orderDetails.setProductId(cart.getProduct().getProductId());
                        orderDetails.setUnit_price(cart.getProduct().getUnitPrice());
                        orderDetails.setName(cart.getProduct().getProductName());
                        orderDetails.setOrder_quantity(cart.getQuantity());
                        // thực hiện add và list và lưu vào file
                        OrderDetailsImpl.orderDetailsList.add(orderDetails);
                        IOFILE.writeListToFile(OrderDetailsImpl.orderDetailsList, ShopConstanst.ORDER_Details_PATH);
                        cartsDelete.add(cart);
                    }
                }
                for (Cart cart : cartsDelete) {
                    if(cart.getProduct().getStockQuantity() < cart.getQuantity()){
                        System.err.println("Không đủ số lượng");
                        return;
                    }
                }
                // thay doi so luong san pham
                for (Cart cart:cartsDelete){
                    Product product = cart.getProduct();
                    product.setStockQuantity(product.getStockQuantity() - cart.getQuantity());
                    IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);
                }
                System.out.println(Colors.PURPLE+"Cập nhật thành công"+Colors.RESET);
                // xóa toàn bộ giỏ hàng
                CartImpl.cartList.removeAll(cartsDelete);
                IOFILE.writeListToFile(CartImpl.cartList,ShopConstanst.CART_PATH);
                break;
            } else {
                System.err.println("Không tìm thấy địa chỉ");
            }
        }

    }

    private static void editQuantityCart(Scanner sc) {
        //Kiểm tra xem có giỏ hàng của người đang đăng nhập không
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống!!!");
            System.out.println();
            return;
        }
        //Hiể thị thông tin giỏ hàng của người dùng hiện tại

        CartImpl.cartList.stream().filter(cart -> cart.getUserId() == Main.userLogin.getId()).forEach(Cart::displayData);
        // Ensure there are cart items for the logged-in user
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống");
            return;
        }

        // Input cart ID
        int cartId;
        while (true) {
            try {
                System.out.println("Nhập ID giỏ hàng");
                cartId = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Bạn phải nhập số nguyên, vui lòng nhập lại");
            }
        }
        int newCartID = cartId;

        // Nhập số lượng mới
        int qtyCart;
        while (true) {
            try {
                System.out.println("Nhập số lượng bạn muốn sửa quantity");
                qtyCart = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Bạn nhập vào số nguyên nhé");
            }
        }

        // Tìm giỏ hảng có ID của người dùng đăng nhập
        Optional<Cart> cartOptional = CartImpl.cartList.stream()
                .filter(cart -> cart.getUserId() == Main.userLogin.getId() && cart.getId() == newCartID)
                .findFirst();

        if (cartOptional.isEmpty()) {
            System.err.println("Không tìm thấy ID giỏ hàng hoặc giỏ hàng không thuộc về bạn");
            return;
        }

        Cart cartItem = cartOptional.get();
        Product product = cartItem.getProduct();

        int currentQuantity = cartItem.getQuantity();
        // số lượng sản phẩm hiện có trong giỏ hàng
        int difference = qtyCart - currentQuantity;
        // khác biệt giữa số lượng sản mới và hiện tại

        // Update the product stock
        if (difference > 0) {
            if (difference > product.getStockQuantity()) {
                System.err.println("Không đủ hàng trong kho, vui lòng thử lại");
                return;
            }
            product.setStockQuantity(product.getStockQuantity() - difference);
        } else {
            product.setStockQuantity(product.getStockQuantity() - difference);
        }
        IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);

        // Update the cart item quantity
        cartItem.setQuantity(qtyCart);
        IOFILE.writeListToFile(CartImpl.cartList, ShopConstanst.CART_PATH);
        System.out.println("Cập nhật số lượng thành công");
    }

    private static void deleteAllCart(Scanner scanner) {
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống");
            return;
        }
        List<Cart> cartNew = new ArrayList<>();
        for (Cart cart : CartImpl.cartList) {
            if (cart.getUserId() == Main.userLogin.getId()) {
                cartNew.add(cart);
            }
        }

        for (Cart cart : cartNew) {
            CartImpl.cartList.remove(cart);
        }
        System.out.println("Xóa toàn bộ thành công");
//        CartImpl.cartList.removeIf(cart -> cart.getUserId() == Main.userLogin.getId());
        //Nếu bạn muốn xóa trực tiếp các phần tử khỏi danh sách cartList mà không cần tạo danh sách cartNew, bạn có thể sử dụng phương thức removeIf():
        //C2
//        CartImpl.cartList.removeIf(cart -> cart.getUserId() == Main.userLogin.getId());

    }

    private static void deleteCartByID(Scanner scanner) {
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống");
            return;
        }
        int idDelete;
        while (true) {
            try {
                System.out.println("Mời bạn nhập mã ID xóa");
                idDelete = Integer.parseInt(scanner.nextLine());
                if (idDelete <= 0) {
                    System.err.println("Bạn phải nhập số lớn hơn ko");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }
        }
        int newidDelte = idDelete;
        boolean isExist = CartImpl.cartList.stream().
                filter(cart -> cart.getUserId() == Main.userLogin.getId()).
                anyMatch(cart -> cart.getId() == newidDelte);
        if (!isExist) {
            System.err.println("Không tìm thấy ID cần xóa " + newidDelte);

        } else {
            cartImpl.remove(newidDelte);
            System.out.println("Xóa thành công");
        }
    }


    private static void addToCart(Scanner scanner) {
        if (ProductImpl.productList.isEmpty()) {
            System.err.println("Danh sách trống");
            return;
        }
        if (ProductImpl.productList != null && !ProductImpl.productList.isEmpty()) {
            System.out.println(Colors.CYAN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
            System.out.println(Colors.CYAN + "┃                             ** LIST PRODUCTS **                        ┃" + Colors.RESET);
            System.out.println(Colors.CYAN + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

            ProductImpl.productList.stream()
                    .filter(product -> product.getCategory().isStatus()) // Ensure category is not null
                    .forEach(Product::displayDataForUser); // Display data of products that belong to active categories
        } else {
            System.out.println("Không có sản phẩm nào");
        }

        // Nhập ID sản phẩm từ người dùng
        System.out.println("Mời bạn nhập ID sản phẩm bạn muốn thêm vào giỏ hàng");
        int productIdOld;
        while (true){
            try {
                productIdOld = Integer.parseInt(scanner.nextLine());
                if(productIdOld<=0){
                    System.err.println("Bạn phải nhập số lớn hơn 0");
                    continue;
                }
                break;

            }
            catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }

        }
        int productId= productIdOld;

        //Tìm sản phẩm trong danh sách sản phẩm
        Product product = ProductImpl.productList.stream().filter(p -> p.getProductId() == productId).findFirst().orElse(null);
        Optional<Product> products=ProductImpl.productList.stream().filter(product1 -> product1.getProductId()==productId).findFirst();
        if (product == null) {
            System.err.println("Không tìm thấy ID sản phẩm " + productId);
            return;
        }
        //Tìm giỏ hàng của người đang đăng nhập
        Cart existingCart = CartImpl.cartList.stream()
                .filter(cart -> cart.getUserId() == Main.userLogin.getId() && cart.getProduct().getProductId() == productId)
                .findFirst()
                .orElse(null);
//        Optional<Cart> existingCart = CartImpl.cartList.stream()
//                .filter(cart -> cart.getUserId() == Main.userLogin.getId()
//                        && cart.getProduct().getProductId() == productId)
//                .findFirst();
        // kiểm tra xử lý nếu có giá trị isPresent
        // sử dụng ifPresent xử lý khi tìm thấy Cart
        //orElse nếu muốn trả về một giá trị mặc định
        //orElseThrow xư lý ngoại lệ nếu k thấy cart
        if (existingCart != null) {
            // xử lý nếu sản phẩm đã có trong giỏ hàng

            int qtyProduct;
            while (true) {
                System.out.println("Sản phẩm đã có trong giỏ hàng, vui lòng nhập số lượng muốn thêm vào");
                while (true){
                    try {
                        qtyProduct = Integer.parseInt(scanner.nextLine());
                        if (qtyProduct <= 0) {
                            System.err.println("Bạn phải nhập số lớn hơn không");
                            continue;
                        }
                        break;
                    }catch (NumberFormatException e) {
                        System.err.println("Bạn phải nhập số nguyên");
                    }
                }
                if (qtyProduct > product.getStockQuantity()) {
                    System.err.println("Sản phẩm đã vượt quá số lượng hiện có");
                }
                break;

            }
            existingCart.setQuantity(existingCart.getQuantity() + qtyProduct);
//            product.setStockQuantity(product.getStockQuantity() - qtyProduct);
            IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);
            System.out.println("Cập nhập giỏ hàng thành công");
        } else {
            //Nếu sản phẩm chưa có trong giỏ hàng
            Cart newCart = new Cart();
            newCart.inputDataCart(scanner, productId);
            newCart.setUserId(Main.userLogin.getId());
            //Đảm bảo giỏ hàng thuộc về người  đang đăng nhập
            cartImpl.addAndUpdate(newCart);
//            product.setStockQuantity(product.getStockQuantity() - newCart.getQuantity());
            IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);
            System.out.println("Thêm vào giỏ hàng thành công");
        }
    }

    private static void showAllCart() {
        System.out.println();
        if (CartImpl.cartList.stream().noneMatch(cart -> cart.getUserId() == Main.userLogin.getId())) {
            System.err.println("Danh sách trống!!!");
            System.out.println();
            return;
        }

        CartImpl.cartList.stream().filter(cart -> cart.getUserId() == Main.userLogin.getId()).forEach(Cart::displayData);
        double totalPrice=CartImpl.cartList.stream().
                filter(cart1 -> cart1.getUserId() == Main.userLogin.getId())
                .map(item -> item.getQuantity() * item.getProduct().getUnitPrice())
                .reduce(0.0,Double::sum);
        Locale localeVn=new Locale("vi","VN");
        NumberFormat currencyFormat=NumberFormat.getCurrencyInstance(localeVn);

        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("┃" +Colors.YELLOW + " %-72s " +Colors.PURPLE + "┃" + Colors.YELLOW + " %-14s " + Colors.PURPLE + "┃\n", "Tổng số tiền cần thanh toán là:", currencyFormat.format(totalPrice));
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }
}
