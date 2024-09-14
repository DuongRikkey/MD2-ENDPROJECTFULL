package business.features.impl.oderImpl;

import business.entity.Order;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderImpl implements IGenericDesign<Order,Integer> {
    public static List<Order> orderList = IOFILE.getListFromFile(ShopConstanst.ORDER_PATH);


    @Override
    public void addAndUpdate(Order order) {
        int checkIndex=findIndexbyID(order.getOrderId());
        if(checkIndex==-1){
            orderList.add(order);
            order.setOrderId(getNewId());
        }
        else{
            orderList.set(checkIndex, order);
        }
        IOFILE.writeListToFile(orderList,ShopConstanst.ORDER_PATH);
    }

    @Override
    public void remove(Integer id) {
     orderList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(orderList,ShopConstanst.ORDER_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return orderList.stream().map(Order::getOrderId).toList().indexOf(id);
    }

    @Override
    public Integer getNewId() {
        Optional<Order> optionalOrder=orderList.stream().max(Comparator.comparingInt(Order::getOrderId));
        if(optionalOrder.isPresent()){
            return optionalOrder.get().getOrderId()+1;
        }
        return 1;
    }
}
