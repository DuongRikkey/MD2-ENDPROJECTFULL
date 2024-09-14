package business.utils;

import business.entity.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFILE {
    public static <T> List<T> getListFromFile(String path) {
        List<T> list = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            // Đọc danh sách từ tệp, giả định rằng dữ liệu trong tệp là một danh sách đối tượng
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                // Ép kiểu an toàn với kiểm tra loại
                list = (List<T>) obj;
            }
        } catch (EOFException e) {
            // Xử lý trường hợp tệp rỗng hoặc đến cuối tệp
            System.out.println("Tệp rỗng hoặc đã đến cuối tệp.");
        } catch (IOException e) {
            // Xử lý lỗi đọc/ghi tệp
            System.err.println("Lỗi đọc tệp: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Xử lý lỗi khi không tìm thấy lớp cần thiết
            System.err.println("Lớp không tìm thấy: " + e.getMessage());
        }
        return list;
    }
    public static <T> boolean writeListToFile(List<T> list, String path) {
        if (list == null) {
            System.err.println("Danh sách không thể là null.");
            return false;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        List<Users> usersList = getListFromFile(ShopConstanst.USER_PATH);
        for (Users users : usersList) {
            users.displayData();
        }
    }
}
