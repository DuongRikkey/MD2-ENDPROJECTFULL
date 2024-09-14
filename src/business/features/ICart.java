package business.features;

import business.entity.Cart;
import business.entity.Product;

import java.util.List;
import java.util.Scanner;

public interface ICart<T,E> {
    void inputData(Scanner sc, List<Product> products,List<Cart> carts);
    void displayData();
}
