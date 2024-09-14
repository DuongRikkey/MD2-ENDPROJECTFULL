package business.features.impl;

import business.entity.WishList;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WishListImpl implements IGenericDesign<WishList,Integer> {
    public static List<WishList> wishLists = IOFILE.getListFromFile(ShopConstanst.WISHLIST_PATH);
    @Override
    public void addAndUpdate(WishList wishList) {
      int indexcheck=findIndexbyID(wishList.getWishListID());
      if(indexcheck==-1){
          wishLists.add(wishList);
          wishList.setWishListID(getNewId());
      }else {
          wishLists.set(indexcheck, wishList);
      }
      IOFILE.writeListToFile(wishLists, ShopConstanst.WISHLIST_PATH);
    }

    @Override
    public void remove(Integer id) {
        wishLists.remove(findIndexbyID(id));
        IOFILE.writeListToFile(wishLists, ShopConstanst.WISHLIST_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return wishLists.stream().map(WishList::getWishListID).toList().indexOf(id);
    }

    @Override
    public Integer getNewId() {
        Optional<WishList> optionalWishList=wishLists.stream().max(Comparator.comparingInt(WishList::getWishListID));
        if(optionalWishList.isPresent()){
            return optionalWishList.get().getWishListID()+1;
        }
        return 1;
    }

}
