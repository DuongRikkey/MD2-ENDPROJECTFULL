package presentation.admin.userManagerMenu;

import business.constants.RoleName;
import business.entity.Users;
import business.features.IGenericDesign;
import business.features.impl.PaginationHelper.PaginationHelper;
import business.features.impl.UsersImpl;
import business.utils.Colors;
import business.utils.InputMethod;
import presentation.run.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class userManagerMenu {
    public static IGenericDesign usersImpl =new UsersImpl();
    public void showUserMenu(Scanner scanner) {
        boolean flag = true;
        do {

            // Define ANSI escape codes for colors
            final String PURPLE = "\033[35m";  // Purple for borders
            final String YELLOW = "\033[33m";  // Yellow for text
            final String BLUE = "\033[34m";    // Blue for highlights
            final String GREEN = "\033[32m";   // Green for special text
            final String RESET = "\033[0m";    // Reset to default color




            System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                          USER MANAGER MENU                            " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   1. Hiển thị toàn bộ người dùng                      " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   2. Cập nhật trạng thài (Open/Block)                 " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   3. Xóa người dùng                                   " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   4. Tìm kiếm người dùng theo tên                     " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   5. Hiển thị Role                                    " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┃" + YELLOW + "                   6. Trở lại                                          " + PURPLE + "┃");
            System.out.println("┃                                                                       ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
            System.out.print("Enter Your Choice: ");



            int choice = InputMethod.getInteger();
            switch(choice) {
                case 1:{
                    showAllUser();
                    break;
                }
                case 2:{
                    editUser(scanner);
                    break;
                }
                case 3:{
                    deleteUser(scanner);
                    break;
                }
                case 4:{
                    searchUser(scanner);
                    break;
                }
                case 5:{
                    showRole(scanner);
                    break;
                }
                case 6:{
                    flag = false;
                    break;
                }
                default:
                    System.err.println("Lựa chọn 1 -> 6");
            }

        }while(flag);

    }

    private void showRole(Scanner scanner) {
        RoleName[] roless=RoleName.values();
        int count=1;
        System.out.println("***************LIST ROLES***************");
        for(RoleName role:roless) {
            System.out.println(count+"."+role);
            count++;
        }
        System.out.println("Có "+(count-1)+ " roles");

        List<RoleName> roles=new ArrayList<>();
        for(RoleName role:roless) {
            roles.add(role);
        }
        roles.stream().forEach(roleName -> {System.out.println("Vai trò" +roleName);
        UsersImpl.usersList.stream().filter(users -> users.getRoleName()==roleName).forEach(users -> System.out.println("Tên "+users.getUsername()));}
        );
    }

    private void searchUser(Scanner scanner) {
        System.out.println("Mời bạn nhập tên người dùng  cần tìm ");
        String name=InputMethod.getString().toLowerCase();
        List<Users> matchingUser=UsersImpl.usersList.stream().filter(users -> users.getFullName().toLowerCase().contains(name)).toList();
        if(matchingUser.isEmpty()) {
            System.err.println("Danh sách trống không thể tìm");
        }else {
            matchingUser.forEach(Users::displayData);
        }

    }

    private void deleteUser(Scanner scanner) {
        int idDelete ;
        System.out.println("Mời bạn nhập ID người dùng muốn xóa");
        while (true){
            try {
                idDelete = Integer.parseInt(scanner.nextLine());
                if (idDelete <=0) {
                    System.err.println("Bạn phải nhập Id lớn hơn không");
                    continue;
                }
                break;

            }catch (NumberFormatException e) {
                System.err.println("Bạn phải nhập số nguyên");
            }
        }
        int finalidDelete = idDelete;
        boolean isExit = UsersImpl.usersList.stream().anyMatch(user -> user.getId() == finalidDelete);
        if (!isExit) {
            System.err.println("Không tìm thấy id cần xóa"+idDelete);
        }else {
            Users users=UsersImpl.usersList.get(usersImpl.findIndexbyID(finalidDelete));
            if (users!=null) {
                if (users.getRoleName()==RoleName.ROLE_ADMIN){
                    System.err.println("Không thể xóa được Admin ");
                }
                else {
                   users.setDeleted(true);
                }
            }
            else {
                System.err.println("Danh sách người dùng trống");
            }

        }
//
     }

    private void editUser(Scanner scanner) {
        int idUpdateUser;
        while (true) {
            try {
                System.out.println("Mời bạn nhập ID muốn Update");
                idUpdateUser = Integer.parseInt(scanner.nextLine());
                if (idUpdateUser <= 0) {
                    System.err.println("Bạn phải nhập Id lớn hơn không");
                    continue;
                }
                break;
            }catch (NumberFormatException e) {
                System.err.println("ID phải là số nguyên");
            }
        }
        int userIndex=usersImpl.findIndexbyID(idUpdateUser);
        if (userIndex==-1) {
            System.err.println("Không tìm thấy ID cần update"+userIndex);
        }
        else {
            Users oldUser = UsersImpl.usersList.get(userIndex);
            if (oldUser != null) {
                // Check if the user is an admin and the logged-in user is not allowed to modify admins
                if (oldUser.getRoleName() == RoleName.ROLE_ADMIN) {
                    System.err.println("Đây là Admin, bạn không có quyền chỉnh sửa");
                } else {
                    // Input new data for the user
                    oldUser.setStatus(!oldUser.getStatus());

                    // Update the user in the list
                    usersImpl.addAndUpdate(oldUser);
                    System.out.println("Cập nhật thông tin người dùng thành công.");
                }
            } else {
                System.err.println("Không thể tìm thấy người dùng có ID: " + idUpdateUser);
            }
        }

    }


    private void showAllUser() {
        int USERS_PER_PAGE = 3;
       List<Users> usersList=UsersImpl.usersList;
        PaginationHelper.showPaginatedList(usersList, USERS_PER_PAGE);

        }
    }

