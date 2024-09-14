package business.entity;

import business.constants.RoleName;
import business.features.IODATA;
import business.features.impl.UsersImpl;
import business.utils.InputMethod;

import javax.print.DocFlavor;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Users  implements Serializable, IODATA<Users,String> {

    private int id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private Boolean status;
    private String phone;
    private String address;
    private Date created;
    private Date updated;
    private RoleName roleName;
    private Boolean isDeleted;
    private int wallet;

    public Users() {
    }

    public Users(int id, String username, String fullName, String email, String password, Boolean status, String phone, String address, Date created, Date updated, RoleName roleName, Boolean isDeleted,int wallet) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.phone = phone;
        this.address = address;
        this.created = created;
        this.updated = updated;
        this.roleName = roleName;
        this.isDeleted = isDeleted;
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public void inputData(Scanner scanner, boolean isAdd){
        if(isAdd){
            this.created=new Date();
            this.username=inputUserName(scanner);
            this.password=inputPassword(scanner);
        }

        this.fullName=inputFullName(scanner);
        this.email=inputEmail(scanner);
        this.address=inputAdress();
        this.phone=inputPhone(scanner);
        this.roleName=RoleName.ROLE_USER;
        this.updated=new Date();
        this.status=true;
        this.isDeleted=false;
        this.wallet=0;
    }

    private int inputWallet(Scanner scanner) {
        System.out.println("Mời bạn nhập Wallet của khách hàng");
        int wallet=InputMethod.getInteger();
        return wallet;
    }
    final String PURPLE = "\033[35m";  // Purple for borders
    final String CYAN = "\033[36m";    // Cyan for headers
    final String YELLOW = "\033[33m";  // Yellow for text
    final String GREEN = "\033[32m";   // Green for active status
    final String RED = "\033[31m";     // Red for inactive status
    final String RESET = "\033[0m";    // Reset to default color

    @Override
    public void displayData() {
        Locale localeVN = new Locale("vi","VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + CYAN + "                           USER MANAGER                                " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Row 1: ID, Username, Fullname
        System.out.printf("┃" + YELLOW + " %-5s" + PURPLE + "┃" + YELLOW + " %-20s" + PURPLE + "┃" + YELLOW + " %-15s" + PURPLE + "┃" + YELLOW + " %-24s" + PURPLE + "┃\n",
                "ID", "Username","Wallet","Fullname");
        System.out.printf("┃ %-4d " + PURPLE + "┃ %-19s " + PURPLE +"┃ %-14s " + PURPLE + "┃ %-23s " + PURPLE + "┃\n",
                this.id, this.username,currencyFormatter.format(this.wallet), this.fullName);

        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Row 2: Role, Created, Updated
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-31s" + PURPLE + "┃" + YELLOW + " %-24s" + PURPLE + "┃\n",
                "Role", "Created", "Updated");
        System.out.printf("┃ %-10s " + PURPLE + "┃ %-30s " + PURPLE + "┃ %-23s " + PURPLE + "┃\n",
                this.roleName, dateFormat.format(this.created), dateFormat.format(this.updated));

        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Row 3: Status, isDeleted
        System.out.printf("┃" + YELLOW + " %-11s" + PURPLE + "┃" + YELLOW + " %-31s" + PURPLE + "┃"+ YELLOW + " %-23s " + PURPLE + "┃\n",
                "Status", "isDeleted", "Email");
        System.out.printf("┃ %-10s " + PURPLE + "┃ %-30s " + PURPLE + "┃ %-23s " + PURPLE + "┃\n",
                this.status ? "Hoạt động" : "Block",
                this.isDeleted ? "Bị xóa" : "Hoạt động",
                this.email);

        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }



    public void displayDataforUser(){
        Locale localeVN = new Locale("vi","VN");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        System.out.println(PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃" + CYAN + "                           USER INFORMATION                            " + PURPLE + "┃");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");

        // Row 1: ID, Username, Fullname
        System.out.printf("┃" + YELLOW + " %-5s" + PURPLE + "┃" + YELLOW + " %-20s" + PURPLE + "┃" + YELLOW + " %-15s" + PURPLE + "┃" + YELLOW + " %-24s" + PURPLE + "┃\n",
                "ID", "Username","Wallet","Fullname");
        System.out.printf("┃ %-4d " + PURPLE + "┃ %-19s " + PURPLE +"┃ %-14s " + PURPLE + "┃ %-23s " + PURPLE + "┃%-30s |\n",
                this.id, this.username,currencyFormatter.format(this.wallet), this.fullName,markPassword(this.password));
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);

    }
    //password muốn **** có thể dùng repalce(password,repeat"*".password.length)
    private String markPassword(String password){
        StringBuilder markPass=new StringBuilder();
        //append nối thêm chuỗi vào cuối đối tượng

        // insert(int offset, String str): Chèn chuỗi str vào vị trí offset

        //replace thay thế chuỗi từ start tới end (int start, int end, String str)
        //Thay thế chuỗi từ vị trí start đến end bằng str

        //delete(int start,int end):Xóa chuôi từ vị tr start đến end
        //deleteCharAt(int index): xóa ký tự tại ví trí index
        //CharAt: lấy vị trí tại index
        //setCharAt(int index, char):Thay thế ký tự index bằng ký tự char
        //substring trả về chuỗi con
        // toString chuyển đối tượng stringBuilder thành String

        for (int i=0;i< password.length();i++){
            markPass.append("*");
        }
        return markPass.toString();
    }



    public void inputLogin(Scanner scanner){
        this.username=inputName(scanner);
        this.password=inputPassword(scanner);
    }

    private String inputName(Scanner scanner) {
        System.out.println("Mời bạn nhập tên tài khoản");
    do {
        String name=scanner.nextLine();
        if (name.isBlank()){
            System.err.println("Không để trống tên");
        }else {
            return name;
        }

    }while (true);
    }

    private String inputAdress() {
        System.out.println("Mời bạn nhập địa chỉ");
        return InputMethod.getString();
    }

    private String inputPhone(Scanner scanner) {
        String regionVn = "+84";
        String phoneRegex = "";
        do {
            System.out.println("Nhập số điện thoại");
            long phone = InputMethod.getLong();
            phoneRegex = String.valueOf(phone);
            if (phoneRegex.length()==9) {
                break;
            }
            else{
                System.err.println("Số điện thoại phải bao gồm 10 chữ số");
            }
        }while (true);
        return regionVn + phoneRegex;
    }

    private String inputEmail(Scanner scanner) {
        System.out.println("Mời bạn nhập email");

        // Check if the user list is not null
        if (UsersImpl.usersList == null) {
            throw new IllegalStateException("Danh sách người dùng chưa được khởi tạo.");
        }

        while (true) {
            String email = scanner.nextLine().trim();

            if (email.isBlank()) {
                System.err.println("Không để trống email");
            } else if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                boolean isExist = UsersImpl.usersList .stream().anyMatch(user -> user.getEmail().equals(email));

                if (isExist) {
                    System.err.println("Email đã bị trùng");
                } else {
                    return email;
                }
            } else {
                System.err.println("Không đúng định dạng email");
            }
        }
    }

    private String inputFullName(Scanner scanner) {
        System.out.println("Mời bạn nhập Họ và tên");
        do {
            String fullName = scanner.nextLine();
            if (fullName.isBlank()) {
                System.err.println("Không để trống tên");
            }
            else {
                return fullName;
            }

        }while(true);
    }

    private String inputPassword(Scanner scanner) {
        System.out.println("Mời bạn nhập mật khẩu");
        do {
            String password=scanner.nextLine();
            if (password.length()<6) {
                System.err.println("Mật khẩu phải lớn hơn 6 ký tự ");
            }else {
                if (password.matches("^(?=.*[0-9])(?=.*[A-Z]).+$")) {
                    return password;
                }
                else {
                    System.err.println("Mật khẩu phải đúng quy định vd Hello123 có ít nhất 1 chữ số vã chữ cái in hoa");
                }
            }

        }while(true);

    }

    private String inputUserName(Scanner scanner) {
        System.out.println("Nhập tên tài khoản ");
        do {
            String userName = scanner.nextLine().trim(); // Loại bỏ khoảng trắng ở đầu/cuối
            if (userName.isBlank()) {
                System.err.println("Không để trống tên");
            } else {
                if (userName.matches("^[a-zA-Z0-9]{6,100}$")) {
                    // Kiểm tra xem danh sách usersList đã được khởi tạo chưa
                    if (UsersImpl.usersList != null && !UsersImpl.usersList.isEmpty()) {
                        boolean isExist = UsersImpl.usersList.stream()
                                .anyMatch(e -> e.getUsername().equalsIgnoreCase(userName));
                        if (isExist) {
                            System.err.println("Tên đăng nhập đã bị trùng");
                        } else {
                            return userName;
                        }
                    } else {
                        // Nếu danh sách rỗng hoặc null, không cần kiểm tra trùng lặp
                        return userName;
                    }
                } else {
                    System.err.println("Tên tài khoản chỉ chứa ký tự chữ và số, từ 6 đến 100 ký tự");
                }
            }
        } while (true);
    }

}
