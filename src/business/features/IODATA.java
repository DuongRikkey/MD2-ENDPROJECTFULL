package business.features;

import java.util.Scanner;

public interface IODATA <T,E>{
    void inputData(Scanner sc, boolean isAdd);
    void displayData();
}
