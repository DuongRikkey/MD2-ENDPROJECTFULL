package business.features.impl.oderImpl;

import business.constants.Status;
import business.entity.Order;
import business.entity.OrderDetails;
import business.entity.Product;
import business.entity.Users;
import business.features.impl.CartImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.UsersImpl;
import business.features.impl.orderDetails.OrderDetailsImpl;
import business.features.impl.productImpl.Admin.ProductImpl;
import business.utils.Colors;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;
import presentation.admin.odermenu.OrderMenu;
import presentation.run.Main;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderFeatures {
    static ProductImpl productImpl = new ProductImpl();

    public static void showAllDrder(boolean isAdmin) {
        if (!isAdmin) {
            if (OrderImpl.orderList.stream().noneMatch(order -> order.getUserId() == Main.userLogin.getId())) {
                System.err.println("Danh sách order trống");
                System.out.println();
                return;
            }
            ;
        } else {
            if (OrderImpl.orderList.isEmpty()) {
                System.err.println("Danh sách order trống");
                return;
            }
        }

        if (isAdmin) {
            int itemsPerPage = 3;
            List<Order> orders = OrderImpl.orderList;
            PaginationHelper.showPaginatedList(orders, itemsPerPage);
        } else {
            int itemsPerPage = 3;
            List<Order> orderList = OrderImpl.orderList.stream().filter(order -> order.getUserId() == Main.userLogin.getId()).toList();
            PaginationHelper.showPaginatedList(orderList, itemsPerPage);
        }
    }

    public static void showDetailOrder(boolean isAdmin) {
        if (isAdmin) {
            System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃" + Colors.CYAN + "                              ORDER                                    " + Colors.PURPLE +  "┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

            OrderImpl.orderList.forEach(Order::displayData);
        }else{
            OrderImpl.orderList.stream().filter(order -> order.getUserId() == Main.userLogin.getId()).forEach(Order::displayData);
        }
//
        if (!isAdmin) {
            // Kiểm tra nếu không có đơn hàng nào thuộc về người dùng hiện tại
            if (OrderImpl.orderList.stream().noneMatch(order -> order.getUserId() == Main.userLogin.getId())) {
                System.err.println("Danh sách order trống");
                return;
            }
        } else {
            // Nếu là admin, kiểm tra xem danh sách đơn hàng có trống không
            if (OrderImpl.orderList.isEmpty()) {
                System.err.println("Danh sách order trống");
                return;
            }
        }

        System.out.println(Colors.CYAN + "Mời bạn nhập ID Order muốn show chi tiết" + Colors.RESET);
        int idOrderCheck = InputMethod.getInteger();

        if (isAdmin) {
            // Admin có thể xem tất cả các đơn hàng
            if (OrderMenu.orderImpl.findIndexbyID(idOrderCheck) != -1) {
                OrderDetailsImpl.orderDetailsList.stream()
                        .filter(orderDetails -> orderDetails.getOrderId() == idOrderCheck)
                        .forEach(OrderDetails::displayData);
                return;
            }
        } else {
            // Nếu không phải admin, chỉ cho người dùng đã đăng nhập xem đơn hàng của họ
            int indexOrder = OrderMenu.orderImpl.findIndexbyID(idOrderCheck);

            // Kiểm tra nếu đơn hàng có tồn tại
            if (indexOrder != -1) {
                Order order = OrderImpl.orderList.get(indexOrder); // Lấy thông tin đơn hàng theo ID

                // Kiểm tra xem người dùng đăng nhập có phải là chủ sở hữu của đơn hàng không
                if (order != null && order.getUserId() == Main.userLogin.getId()) {
                    OrderDetailsImpl.orderDetailsList.stream()
                            .filter(orderDetails -> orderDetails.getOrderId() == idOrderCheck)
                            .forEach(OrderDetails::displayData);
                } else {
                    System.out.println(Colors.RED + "Không tìm thấy đơn hàng" + Colors.RESET);
                }
            } else {
                System.out.println(Colors.RED + "ID đơn hàng không hợp lệ!" + Colors.RESET);
            }
        }
        // Nhập vào id order muốn show chi tiet
        // tìm ra danh sach orderDetail dua vao orderId trong từng orderDetail
        // hiển thị danh sách orderDetail
    }

    public static void showOrderByStatus(boolean isAdmin) {

        if (!isAdmin) {
            // Kiểm tra nếu không có đơn hàng nào thuộc về người dùng hiện tại
            if (OrderImpl.orderList.stream().noneMatch(order -> order.getUserId() == Main.userLogin.getId())) {
                System.err.println("Danh sách order trống");
                return;
            }
        } else {
            // Nếu là admin, kiểm tra xem danh sách đơn hàng có trống không
            if (OrderImpl.orderList.isEmpty()) {
                System.err.println("Danh sách order trống");
                return;
            }
        }
        System.out.println(Colors.CYAN + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                                     ┃");
        System.out.println("┃" + Colors.PURPLE+ "                           DANH SÁCH TRẠNG THÁI                      " + Colors.RESET + "┃");
        System.out.println("┃                                                                     ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);


        Status[] statuses = Status.values();
        int count = 1;
        for (Status status : statuses) {
            System.out.println(count + " . " + status);
            count++;
        }
        String status;
        Status inputStatus;
        while (true) {
            try {
                System.out.println(Colors.CYAN + "Mời bạn nhập trạng thái " + Colors.RESET);
                status = InputMethod.getString();
                inputStatus = Status.valueOf(status);
                break;

            } catch (Exception e) {
                System.err.println("Không chứa status xin vui lòng thử lại");
            }
        }
        Status finalInputStatus = inputStatus;
        if (!isAdmin) {
            if (OrderImpl.orderList.stream().noneMatch(order -> order.getUserId() == Main.userLogin.getId())) {
                System.err.println("Danh sách đơn hàng trống");
            } else {
                System.out.println(Colors.CYAN + "*********************** Order By Status ************************" + Colors.RESET);
                OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(finalInputStatus) && order.getUserId() == Main.userLogin.getId()).forEach(Order::displayData);
                System.out.println(Colors.GREEN + "CÓ " + OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(finalInputStatus) && order.getUserId() == Main.userLogin.getId()).count() + " đơn hàng với trạng thái" + finalInputStatus);

            }


        } else {
            if (OrderImpl.orderList.isEmpty()) {
                System.err.println("Danh sách đơn hàng trống");
            } else {
                System.out.println(Colors.CYAN + "*********************** Order By Status ************************" + Colors.RESET);
                OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(finalInputStatus)).forEach(Order::displayData);
                System.out.println(Colors.GREEN + "Có" + OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(finalInputStatus)).count() + " đơn hàng với trạng thái " + finalInputStatus);
            }
        }


    }
//   //Changed By User
    public static void changeByStatus() {
        Status[] statuses = Status.values();
        //         Hiển thị danh sách đơn hàng của người dùng hiện tại
        OrderImpl.orderList.stream()
                .filter(order -> order.getUserId() == Main.userLogin.getId())
                .forEach(Order::displayData);
//        int PER_PAGE=10;
//        List<Order> orderList=OrderImpl.orderList.stream()
//                .filter(order -> order.getUserId() == Main.userLogin.getId()).toList();
//        PaginationHelper.showPaginatedList(orderList, PER_PAGE);

        System.out.println("Mời bạn chọn ID muốn thay đổi trạng thái đơn hàng");
        int idSelect = InputMethod.getInteger();
        // Tìm đơn hàng dựa trên ID và người dùng hiện tại
        Optional<Order> optionalSelectedOrder = OrderImpl.orderList.stream()
                .filter(order -> order.getOrderId() == idSelect && order.getUserId() == Main.userLogin.getId())
                .findFirst();
        if (optionalSelectedOrder.isEmpty()) {
            System.err.println("Không tìm thấy đơn hàng với ID đã nhập!" +idSelect);
            return;
        }

        Order selectedOrder = optionalSelectedOrder.get();
//        (selectedOrder.getStatus() == Status.WAITING || selectedOrder.getStatus() == Status.CONFIRM)
        if (selectedOrder.getStatus() == Status.WAITING ) {
            System.out.println("Đơn hàng hiện đang ở trạng thái: " + selectedOrder.getStatus());
            System.out.println("Bạn có muốn hủy đơn hàng này không? (Y/N)");

            String confirm = InputMethod.getString().toUpperCase();
            if (confirm.equals("Y")) {
                // Thay đổi trạng thái đơn hàng thành CANCEL
                selectedOrder.setStatus(Status.CANCEL);
                IOFILE.writeListToFile(OrderImpl.orderList, ShopConstanst.ORDER_PATH);
                System.out.println(Colors.GREEN + "Đơn hàng đã được hủy!" + Colors.RESET);

                // Tăng lại số lượng sản phẩm
                List<OrderDetails> orderDetails = OrderDetailsImpl.orderDetailsList.stream()
                        .filter(item -> item.getOrderId() == idSelect)
                        .toList();

                for (OrderDetails item : orderDetails) {
                    Optional<Product> optionalProduct = ProductImpl.productList.stream()
                            .filter(product -> product.getProductId() == item.getProductId())
                            .findFirst();

                    if (optionalProduct.isPresent()) {
                        Product product = optionalProduct.get();
                        // Thay đổi số lượng sản phẩm
                        product.setStockQuantity(product.getStockQuantity() + item.getOrder_quantity());

                        // Cập nhật danh sách sản phẩm vào tệp
                        IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);

                    } else {
                        System.err.println("Không tìm thấy sản phẩm với ID " + item.getProductId());
                    }
                }
//
//                Lấy thông tin người dùng và hoàn tiền
                Optional<Users> optionalUsers=UsersImpl.usersList.stream().filter(users -> users.getId() == Main.userLogin.getId()).findFirst();
                if (optionalUsers.isPresent()) {
                    Users users = optionalUsers.get();
                    //Tính toán số tiền hoàn lại
                    int totalPriceOfOrder=orderDetails.stream().mapToInt(orderDetail->{Optional<Product> optionalProduct=ProductImpl.productList.stream().filter(product -> product.getProductId()==orderDetail.getProductId()).findFirst();
                        if (optionalProduct.isPresent()) {
                            Product product = optionalProduct.get();
                            return (int) (product.getUnitPrice()*orderDetail.getOrder_quantity());

                        }
                        return 0;
                    }).sum();
                    //Cập nhật ví người dùng
                    users.setWallet(users.getWallet()+totalPriceOfOrder);
                    System.out.println();
//                    Locale locale=new Locale("vi","VN");
//                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
//                    // Trap
//                    //Chuyển thành VND
//                    currencyFormat.setCurrency(Currency.getInstance("VND"));
                    System.out.println("Số tiền " + totalPriceOfOrder + " đã được hoàn lại vào ví của khách hàng " + users.getUsername());
                    //Lưu vào file
                    IOFILE.writeListToFile(UsersImpl.usersList, ShopConstanst.USER_PATH);
                }

//



            } else {
                System.out.println(Colors.YELLOW + "Hủy thay đổi trạng thái đơn hàng." + Colors.RESET);
            }
        } else {
            // Nếu đơn hàng không ở trạng thái WAITING hoặc CONFIRM, không cho phép hủy
            System.err.println("Không thể hủy đơn hàng vì trạng thái hiện tại là: " + selectedOrder.getStatus());
        }
    }
    //Changed By Admin
    public static void changeOrderStatus(Scanner scanner) {
        System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
        System.out.println(Colors.PURPLE + "┃                           DANH SÁCH ĐƠN HÀNG                          ┃" + Colors.RESET);
        System.out.println(Colors.PURPLE + "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫" + Colors.RESET);
        OrderImpl.orderList.forEach(Order::displayData);
        System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

        // Nhập ID đơn hàng để thay đổi trạng thái
        System.out.println("Mời bạn lựa chọn ID mà bạn muốn thay đổi trạng thái:");
        int idSelect ;
        while (true){
            idSelect=InputMethod.getInteger();
            if (idSelect <= 0) {
                System.err.println("Không chọn Id nhỏ hơn 0");
                continue;
            }
            break;
        }

        int index = OrderMenu.orderImpl.findIndexbyID(idSelect);
        int newIdSelect=idSelect;
        if (index != -1) {
            Order order = OrderImpl.orderList.get(index);
            Status currentStatus = order.getStatus();

            // Kiểm tra trạng thái hiện tại
            if (currentStatus == Status.CANCEL) {
                System.err.println("Đơn hàng đã khách hàng hủy bỏ. Bạn không thể thay đổi trạng thái của đơn hàng này.");
                return;
            } else if (currentStatus == Status.SUCCESS) {
                System.err.println("Đơn hàng đã xử lý thành công. Không thể thay đổi trạng thái.");
                return;
            } else if (currentStatus == Status.DENIED) {
                System.err.println("Đơn hàng đã bị từ chối. Không thể thay đổi trạng thái.");
                return;
            }

            // Xử lý thay đổi trạng thái dựa trên trạng thái hiện tại
            if (currentStatus == Status.WAITING) {
                // Hiển thị bảng trạng thái mới
                System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃                            CHỌN TRẠNG THÁI MỚI                        ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃        1. CONFIRM      ┃        2. DENIED          ┃        3. HỦY    ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

                // Nhập lựa chọn từ người dùng
                System.out.println("Chọn tùy chọn (1/2/3):");
                int choice = InputMethod.getInteger();

                switch (choice) {
                    case 1:
                        if (isValidTransition(currentStatus, Status.CONFIRM)) {
                            order.setStatus(Status.CONFIRM);
                            System.out.println("Trạng thái đã được cập nhật từ WAITING thành: CONFIRM");
                        } else {
                            System.out.println("Không thể chuyển trạng thái từ WAITING sang CONFIRM.");
                        }
                        break;
                    case 2:
                        if (isValidTransition(currentStatus, Status.DENIED)) {
                            order.setStatus(Status.DENIED);
                            System.out.println("Trạng thái đã được cập từ WAITING nhật thành: DENIED");
                            // Lấy thông tin chi tiết đơn hàng cho đơn hàng cụ thể
                            List<OrderDetails> orderDetailsList = OrderDetailsImpl.orderDetailsList.stream()
                                    .filter(orderDetails -> orderDetails.getOrderId() == newIdSelect)
                                    .toList();
                            // Khôi phục số lượng sản phẩm dựa trên thông tin chi tiết
                            for (OrderDetails orderDetail : orderDetailsList) {
                                Optional<Product> optionalProduct = ProductImpl.productList.stream()
                                        .filter(product -> product.getProductId() == orderDetail.getProductId())
                                        .findFirst();
                                if (optionalProduct.isPresent()) {
                                    Product product = optionalProduct.get();
                                    product.setStockQuantity(product.getStockQuantity() + orderDetail.getOrder_quantity());
                                    productImpl.addAndUpdate(product); // Update product stock
                                }
                            }
                            Optional<Users> optionalUser = UsersImpl.usersList.stream()
                                    .filter(user -> user.getId() == order.getUserId()) // Gỉả sử orderID chứa userID;
                                    .findFirst();

                            if (optionalUser.isPresent()) {
                                Users customer = optionalUser.get();
                                //c2
                                // Calculate the total price of the order for refund
                                //Tính toán tổng giá hoàn trả cho người order
                                int totalPriceOfOrder = orderDetailsList.stream()
                                        .mapToInt(orderDetail -> ProductImpl.productList.stream()
                                                //Phương thức ạnh xạ chuyển đối số Int từng phần tử trong stream thành một giá trị số nguyên
                                                .filter(product -> product.getProductId() == orderDetail.getProductId())
                                                //lọc Id từ product với id trong orderDetail tương ứng

                                                .findFirst()
                                                // sau khi lọc xong lấy phần tử đầu tiên nếu tồn tại
                                                .map(product -> (int) (product.getUnitPrice() * orderDetail.getOrder_quantity()))
                                                .orElse(0)) // Nếu không tìm thấy sản phẩm, trả về 0
                                        .sum();
                                // Hoàn tiền vào ví khách hàng:
                                customer.setWallet(customer.getWallet() + totalPriceOfOrder);
                                // khách hàng thêm vào ví ;
                                Locale locale=new Locale("vi","VN");
                                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

                                System.out.println("Số tiền " + currencyFormat.format(totalPriceOfOrder)  + " đã được hoàn lại vào ví của khách hàng "  +Colors.BLUE+ customer.getUsername()+Colors.RESET);
                                //Lưu update người dùng và sản phẩm vào file
                                IOFILE.writeListToFile(UsersImpl.usersList, ShopConstanst.USER_PATH);
                                IOFILE.writeListToFile(ProductImpl.productList, ShopConstanst.PRODUCT_PATH);
                            } else {
                                System.err.println("Không tìm thấy khách hàng liên quan đến đơn hàng này.");
                            }
                        } else {
                            System.out.println("Không thể chuyển trạng thái từ WAITING sang DENIED.");
                        }
                        break;
                    case 3:
                        System.out.println("Chuyển trạng thái bị hủy.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                        break;
                }
            } else if (currentStatus == Status.CONFIRM) {
                // Hiển thị bảng chọn trạng thái từ CONFIRM
                System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃                            CHỌN TRẠNG THÁI MỚI                        ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃        1. DELIVERY                  ┃        2. HỦY                   ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

                System.out.println("Chọn tùy chọn (1/2):");
                int choice = InputMethod.getInteger();

                if (choice == 1) {
                    if (isValidTransition(currentStatus, Status.DELIVERY)) {
                        order.setStatus(Status.DELIVERY);
                        System.out.println("Trạng thái đã được cập nhật từ CONFIRM  thành: DELIVERY");
                    } else {
                        System.out.println("Không thể chuyển trạng thái từ CONFIRM sang DELIVERY.");
                    }
                } else {
                    System.out.println("Chuyển trạng thái bị hủy.");
                }
            } else if (currentStatus == Status.DELIVERY) {
                // Hiển thị bảng chọn trạng thái từ DELIVERY
                System.out.println(Colors.PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃                            CHỌN TRẠNG THÁI MỚI                        ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┃        1. SUCCESS                   ┃        2. HỦY                   ┃" + Colors.RESET);
                System.out.println(Colors.PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + Colors.RESET);

                System.out.println("Chọn tùy chọn (1/2):");
                int choice = InputMethod.getInteger();

                if (choice == 1) {
                    if (isValidTransition(currentStatus, Status.SUCCESS)) {
                        order.setStatus(Status.SUCCESS);
                        System.out.println("Trạng thái đã được cập nhật từ DELIVERY thành: SUCCESS");
                    } else {
                        System.out.println("Không thể chuyển trạng thái từ DELIVERY sang SUCCESS.");
                    }
                } else {
                    System.out.println("Chuyển trạng thái bị hủy.");
                }
            }

            // Ghi danh sách đơn hàng vào tệp sau khi cập nhật trạng thái
            IOFILE.writeListToFile(OrderImpl.orderList, ShopConstanst.ORDER_PATH);
        } else {
            System.out.println("Không tìm thấy đơn hàng với ID " + idSelect);
        }
    }

    private static boolean isValidTransition(Status currentStatus, Status newStatus) {
        switch (currentStatus) {
            case WAITING:
                return newStatus == Status.CONFIRM || newStatus == Status.DENIED;
            case CONFIRM:
                return newStatus == Status.DELIVERY;
            case DELIVERY:
                return newStatus == Status.SUCCESS;
            default:
                return false;
        }
    }
    public static void searchOrderByStatus(Scanner scanner,boolean isAdmin) {
        Status[] statuses = Status.values();
        int count = 1;
        for (Status status : statuses) {
            System.out.println(count + " . " + status);
            count++;
        }
        if (isAdmin){
            System.out.println("Mời bạn nhập trạng thái cần tìm là");
            String nameSearch = InputMethod.getString().toUpperCase();
            try {
                Status status = Status.valueOf(nameSearch);
                List<Order> matchingStatusOrder = OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(status)).toList();
                if (matchingStatusOrder.isEmpty()) {
                    System.err.println("Không tìm thấy trạng thái cần tìm"+status);
                }else {
                    matchingStatusOrder.forEach(Order::displayData);
                    System.out.println("Có "+OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(status)).count()+" đơn hàng ở trạng  là "+ status);;
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("Trạng thái không hợp lệ. Vui lòng thử lại.");
            }

        }else {
            System.out.println("Mời bạn nhập trạng thái cần tìm là");
            String nameSearch = InputMethod.getString().toUpperCase();
            try {
                Status status = Status.valueOf(nameSearch);
                List<Order> matchingStatusOrder = OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(status)&& order.getUserId()==Main.userLogin.getId()).toList();
                if (matchingStatusOrder.isEmpty()) {
                    System.err.println("Không tìm thấy trạng thái cần tìm"+status);
                }else {
                    matchingStatusOrder.forEach(Order::displayData);
                    System.out.println("Có "+OrderImpl.orderList.stream().filter(order -> order.getStatus().equals(status)).count()+" đon hàng ở trạng thái là "+ status);;
                }
            }
            catch (IllegalArgumentException e){
                System.out.println("Trạng thái không hợp lệ. Vui lòng thử lại.");
            }

        }

    }

    public static void searchOrderByDays(Scanner scanner,boolean isAdmin) throws ParseException {
        if (isAdmin){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Mời bạn nhập ngày bắt đầu(định dạng dd/MM/yyyy))");
            String startDateStr = InputMethod.getString();
            System.out.println("Mời bạn nhập ngày kết thúc(định dạng dd/MM/yyyy))");
            String endDateStr = InputMethod.getString();
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate=sdf.parse(endDateStr);
                if (endDate.before(startDate)) {
                    System.err.println("Ngày kết thúc phải lớn hơn ngày đến sau ");
                    return;
                }
                List<Order> matchingOrders = OrderImpl.orderList.stream()
                        .filter(order -> {
                            Date orderDate = order.getCreatedAt(); // Giả sử Order có phương thức getOrderDate() trả về Date
                            return !orderDate.before(startDate) && !orderDate.after(endDate);
                        })
                        .toList();
                if (matchingOrders.isEmpty()) {
                    System.out.println("Không tìm thấy đơn hàng trong khoảng thời gian từ " + startDateStr + " đến " + endDateStr);
                } else {
                    matchingOrders.forEach(Order::displayData); // Giả sử Order có phương thức displayData() để hiển thị thông tin đơn hàng
                }
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy.");
            }
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Mời bạn nhập ngày bắt đầu(định dạng dd/MM/yyyy))");
            String startDateStr = InputMethod.getString();
            System.out.println("Mời bạn nhập ngày kết thúc(định dạng dd/MM/yyyy))");
            String endDateStr = InputMethod.getString();
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate=sdf.parse(endDateStr);
                if (endDate.before(startDate)) {
                    System.err.println("Ngày kết thúc phải lớn hơn ngày đến sau ");
                    return;
                }
                List<Order> matchingOrders = OrderImpl.orderList.stream()
                        .filter(order -> {
                            Date orderDate = order.getCreatedAt(); // Giả sử Order có phương thức getOrderDate() trả về Date
                            return !orderDate.before(startDate) && !orderDate.after(endDate);
                        }).filter(order -> order.getUserId()==Main.userLogin.getId()).toList();
                if (matchingOrders.isEmpty()) {
                    System.out.println("Không tìm thấy đơn hàng trong khoảng thời gian từ " + startDateStr + " đến " + endDateStr);
                } else {
                    matchingOrders.forEach(Order::displayData); // Giả sử Order có phương thức displayData() để hiển thị thông tin đơn hàng
                }
            } catch (ParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng dd/MM/yyyy.");
            }
        }


    }



}










