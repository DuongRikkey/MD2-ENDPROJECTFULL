package business.features.impl.orderDetails;

import business.entity.OrderDetails;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.List;

public class OrderDetailsImpl implements IGenericDesign<OrderDetailsImpl,Integer> {
    public static List<OrderDetails> orderDetailsList= IOFILE.getListFromFile(ShopConstanst.ORDER_Details_PATH);



    @Override
    public void addAndUpdate(OrderDetailsImpl orderDetails) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public int findIndexbyID(Integer id) {
        return 0;
    }

    @Override
    public Integer getNewId() {
        return 0;
    }
}
