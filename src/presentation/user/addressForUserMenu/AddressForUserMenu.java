package presentation.user.addressForUserMenu;

import business.entity.Address;
import business.entity.Product;
import business.features.IGenericDesign;
import business.features.impl.AddressImpl;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.utils.Colors;
import business.utils.InputMethod;
import presentation.run.Main;

import java.util.List;
import java.util.Scanner;

public class AddressForUserMenu {
    public static IGenericDesign addressImpl =new AddressImpl();
    public static void addressOfUser(Scanner scanner) {
        do {
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String RESET = "\033[0m";    // Reset to default color

            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                        DANH SÁCH ĐỊA CHỈ                              " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị toàn bộ địa chỉ                         " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Thêm địa chỉ mới                                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Chỉnh sửa địa chỉ                                " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Xóa địa chỉ bằng ID                              " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Tìm kiếm địa chỉ qua ID                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Lựa chọn của bạn: ");
            int choice = InputMethod.getInteger();
            switch (choice) {
                case 1:
                    showAddress(scanner);
                    break;
                case 2:
                    addAddress(scanner);
                    break;
                case 3:
                    editAddress(scanner);
                    break;
                case 4:
                    deleteAddress(scanner);
                    break;
                case 5:
                    searchAddressByID(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.err.println("Lựa chọn từ 1 -> 6");
            }
        }while (true);
    }

    private static void searchAddressByID(Scanner scanner) {
        System.out.println("Mời bạn nhập ID cần tìm kiếm");
        int id = InputMethod.getInteger();
        List<Address> matchingAddress=AddressImpl.addressList.stream().filter(address ->address.getUserId()==Main.userLogin.getId() &&address.getAddressId()==id).toList();
        if (matchingAddress.isEmpty()) {
            System.err.println("Không tìm nhấp địa chỉ qua ID là "+id);
        }else {
            matchingAddress.forEach(Address::displayData);
        }
    }

    private static void deleteAddress(Scanner scanner) {
        if (AddressImpl.addressList.stream().noneMatch(address -> address.getUserId()==Main.userLogin.getId())) {
            System.err.println("Danh sách trống");
            return;
        }
        int idDelete ;
        while (true){
            try {
                System.out.println("Mời bạn nhập ID cần Update");
                idDelete= Integer.parseInt(scanner.nextLine());
                if (idDelete<=0){
                    System.err.println("Bạn phải nhập số dương");
                    continue;
                }
                break;

            }catch (NumberFormatException e){
                System.err.println("Bạn phải nhập số nguyê");
            }
        }
        int finalIdDelete = idDelete;
        boolean isExist = AddressImpl.addressList.stream()
                .filter(address -> address.getUserId()==Main.userLogin.getId()).
                anyMatch(address -> address.getAddressId() == finalIdDelete);
        if (!isExist){
            System.err.println("Không tìm thấy ID cần xóa" +finalIdDelete);
        }else {
            addressImpl.remove(finalIdDelete);
            System.out.println("Xóa thành công");
        }
    }

    private static void editAddress(Scanner scanner) {
        int idUpdate=0;
        while (true){
            try {
                System.out.println("Mời bạn nhập ID cần Update");
                idUpdate = Integer.parseInt(scanner.nextLine());
                if (idUpdate<=0){
                    System.err.println("Bạn phải nhập số dương");
                    continue;
                }
                break;
                
            }catch (NumberFormatException e){
                System.err.println("Bạn phải nhập số nguyên");
            }
        }
        int indexIdUpdate = addressImpl.findIndexbyID(idUpdate);

        // Kiểm tra xem địa chỉ có thuộc về người dùng đăng nhập không
        if (indexIdUpdate == -1 || AddressImpl.addressList.get(indexIdUpdate).getUserId() != Main.userLogin.getId()) {
            System.err.println("Không tìm thấy địa chỉ có ID " + idUpdate );
        } else {
            Address oldAddress = AddressImpl.addressList.get(indexIdUpdate);
            oldAddress.inputData(scanner, false); // Cập nhật dữ liệu mới
            addressImpl.addAndUpdate(oldAddress); // Lưu lại thay đổi
            System.out.println("Cập nhật thành công");
        }
    }

    private static void addAddress(Scanner scanner) {
        System.out.println("Mời bạn nhập số lượng địa chỉ muốn thêm vào");
        int number= InputMethod.getInteger();
        for (int i = 0; i < number; i++) {
            System.out.println("Thêm địa chỉ thứ "+(i+1));
            Address address=new Address();
            address.inputData(scanner,true);
            address.setUserId(Main.userLogin.getId());
            addressImpl.addAndUpdate(address);
        }
    }

    private static void showAddress(Scanner scanner) {
        if (AddressImpl.addressList.stream().noneMatch(address -> address.getUserId()==Main.userLogin.getId())){
            System.err.println("*****************************");
            System.err.println("*  Danh sách địa chỉ trống  *");
            System.err.println("*****************************");
            System.out.println();
            return;
        }
//        AddressImpl.addressList.stream().filter(address -> address.getUserId()== Main.userLogin.getId()).forEach(Address::displayData);
        int itemsPerPage = 3;
        List<Address> filteredAddressList=AddressImpl.addressList.stream().filter(address -> address.getUserId()== Main.userLogin.getId()).toList();
        PaginationHelper.showPaginatedList(filteredAddressList,itemsPerPage);

    }
}
