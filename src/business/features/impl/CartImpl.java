package business.features.impl;

import business.entity.Cart;
import business.features.ICart;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CartImpl implements IGenericDesign<Cart,Integer> {
    public static List<Cart> cartList = IOFILE.getListFromFile(ShopConstanst.CART_PATH);
    @Override
    public void addAndUpdate(Cart cart) {
        int indexCheck = findIndexbyID(cart.getId());
        if(indexCheck == -1){
            cartList.add(cart);
            cart.setId(getNewId());
        }else {
            cartList.set(indexCheck, cart);
        }
        IOFILE.writeListToFile(cartList, ShopConstanst.CART_PATH);
    }

    @Override
    public void remove(Integer id) {
       cartList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(cartList, ShopConstanst.CART_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return cartList.stream().map(Cart::getId).toList().indexOf(id);
    }

    @Override
    public Integer getNewId() {
        Optional<Cart> optionalCart=cartList.stream().max(Comparator.comparingInt(Cart::getId));
        if(optionalCart.isPresent()){
            return optionalCart.get().getId()+1;
        }
        return 1;
    }
}
