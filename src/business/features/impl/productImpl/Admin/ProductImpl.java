package business.features.impl.productImpl.Admin;

import business.entity.Product;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductImpl implements IGenericDesign<Product, Integer> {
    public static List<Product> productList = IOFILE.getListFromFile(ShopConstanst.PRODUCT_PATH);

    @Override
    public void addAndUpdate(Product product) {
        int indexCheck = findIndexbyID(product.getProductId());
        if (indexCheck == -1) {
            productList.add(product);
            product.setProductId(getNewId());

        } else {
            productList.set(indexCheck, product);
        }
        IOFILE.writeListToFile(productList, ShopConstanst.PRODUCT_PATH);
    }

    @Override
    public void remove(Integer id) {
        productList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(productList,ShopConstanst.PRODUCT_PATH);;
    }

    @Override
    public int findIndexbyID(Integer id) {
        return productList.stream().map(Product::getProductId).toList().indexOf(id);
    }

    @Override
    public Integer getNewId() {
        Optional<Product> optionalProduct = productList.stream().max(Comparator.comparingInt(Product::getProductId));
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getProductId() + 1;
        }
        return 1;

    }
}
