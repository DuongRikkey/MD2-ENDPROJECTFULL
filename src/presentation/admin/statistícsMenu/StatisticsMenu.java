package presentation.admin.statistícsMenu;

import business.constants.RoleName;
import business.constants.Status;
import business.entity.Order;
import business.entity.OrderDetails;
import business.entity.Users;
import business.features.impl.UsersImpl;
import business.features.impl.oderImpl.OrderImpl;
import business.features.impl.orderDetails.OrderDetailsImpl;
import business.utils.InputMethod;

import javax.management.relation.Role;
import javax.management.relation.RoleStatus;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsMenu {
    public void showStatisticsMenu(Scanner scanner)  {
        boolean isLoop=true;
        do {
            // Define ANSI escape codes for purple borders and yellow text
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          REVENUE MENU                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Thống kê doanh thu theo tháng                       " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thông kê số lượng đơn hàng thành công, hủy theo tháng                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Thông kê doanh thu từ ngày a đến ngày b                              " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Thông kê khách hàng mua hàng nhiều nhất                             " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Quay lại                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");
            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:{
                    new StatisticsMenu().RevenueStatisticsMenu(scanner);
                    break;}

                case 2:{
                    new StatisticsMenu().OrderStatusStatisticsByMonth(scanner);
                    break;

                }
                case 4:{
                    new StatisticsMenu().StatisticsCustomer(scanner);
                }

               case 3:{
                   new StatisticsMenu().StatisticFromATOB(scanner);
                   break;}

            };
        }while (isLoop);


    }

    private void OrderStatusStatisticsByMonth(Scanner scanner) {
        System.out.println("Mời bạn nhập tháng");
        System.out.print("Nhập tháng: ");
        int month;
        while (true){
            month = InputMethod.getInteger();
            if (month >0 && month <=12){
                break;
            }
            else {
                System.err.println("Bạn phải chọn tháng từ 1->12");
            }
        }

        System.out.println("Mời bạn nhập năm");
        int year=InputMethod.getInteger();
        int totalSuccessOrder=0;
        int totalCancelOrder=0;
        int totalDeniedOrder=0;
//
        for (Order order: OrderImpl.orderList){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getCreatedAt());
            int monthOrder=calendar.get(Calendar.MONTH)+1;
            int yearOrder=calendar.get(Calendar.YEAR);
            if (month==monthOrder&&year==yearOrder){
                if (order.getStatus()==Status.SUCCESS){
                    totalSuccessOrder++;
                }
                else if (order.getStatus()==Status.CANCEL){
                    totalCancelOrder++;
                }
                else if (order.getStatus()==Status.DENIED){
                    totalDeniedOrder++;
                }
            }
        }
        System.out.printf("Thông kê đơn hàng tháng %d năm %d",month,year);
        System.out.printf("Đơn hàng thành công %d",totalSuccessOrder);
        System.out.printf("Đơn hàng hủy bỏ %d",totalCancelOrder+totalDeniedOrder);
    }

    private void StatisticFromATOB(Scanner scanner)  {
        List<Order> orders = OrderImpl.orderList;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Nhập ngày bắt đầu (dd/MM/yyyy): ");
        String startDate=scanner.nextLine();
        System.out.println("Nhập ngày kết thúc");
        String endDate=scanner.nextLine();
        try {
            Date startDate1=simpleDateFormat.parse(startDate);
            Date endDate1=simpleDateFormat.parse(endDate);
            if (!startDate1.before(endDate1)) {
                System.err.println("Khônh dc nhot hơn ngày kết thúc");
                return;
            }
            double revenue=0;
            int totalOrder=0;
            for (Order order : OrderImpl.orderList) {
                Date orderDate=order.getCreatedAt();
                if (orderDate.after(startDate1)&&orderDate.before(endDate1)) {
                    revenue+=order.getTotalPrice();
                    totalOrder++;
                }
            }
            Locale locale = new Locale("vn", "VN");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
            System.out.printf("Doanh thu từ %s đến %s là :%s",startDate,endDate,formatter.format(revenue));
            System.out.printf("Tổng đơn từ %s đến %s LÀ %s, ",startDate,endDate,totalOrder);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void RevenueStatisticsMenu(Scanner scanner) {
        System.out.print("Nhập tháng: ");
        int month;
        while (true){
            month = InputMethod.getInteger();
            if (month >0 && month <=12){
                break;
            }
            else {
                System.err.println("Bạn phải chọn tháng từ 1->12");
            }
        }

        System.out.println("Mời bạn nhập năm");
        int year=InputMethod.getInteger();
//        Set<Integer> orderIds = new HashSet<>();
        List<Integer> orderIds =new ArrayList<>();
        for (OrderDetails od: OrderDetailsImpl.orderDetailsList){
            orderIds.add(od.getOrderId());
        }
        double revenue=0;
        int totalOrder=0;
        for (Order order:OrderImpl.orderList){
            if (orderIds.contains(order.getOrderId())){
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(order.getCreatedAt());
                int ordermonth=calendar.get(Calendar.MONTH)+1;
                int orderyear=calendar.get(Calendar.YEAR);
                if (month==ordermonth&&year==orderyear){
                    totalOrder++;
                    revenue+= order.getTotalPrice();
                }
            }
        }
        Locale locale = new Locale("vn", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        System.out.printf("Doanh thu tháng %d tháng, %d năn: %s\n ", month, year, formatter.format(revenue));
        System.out.printf("Số lượng đơn đặt hàng thang %d năm %d ,tổng đơn %d \n",month,year,totalOrder);







    }
}
