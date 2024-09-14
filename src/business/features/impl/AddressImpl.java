package business.features.impl;

import business.entity.Address;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AddressImpl implements IGenericDesign<Address,Integer> {
    public static List<Address> addressList = IOFILE.getListFromFile(ShopConstanst.ADDRESS_PATH);

    @Override
    public void addAndUpdate(Address address) {
        int indexCheck = findIndexbyID(address.getAddressId());
        if (indexCheck == -1) {
            addressList.add(address);
            address.setAddressId(getNewId());
        }
        else {
            addressList.set(indexCheck, address);
        }
        IOFILE.writeListToFile( addressList,ShopConstanst.ADDRESS_PATH);
    }

    @Override
    public void remove(Integer id) {
      addressList.remove(findIndexbyID(id));
        IOFILE.writeListToFile( addressList,ShopConstanst.ADDRESS_PATH);

    }

    @Override
    public int findIndexbyID(Integer id) {
        return addressList.stream().map(Address::getAddressId).toList().indexOf(id);

    }

    @Override
    public Integer getNewId() {
        Optional<Address> optionalAddress=addressList.stream().max(Comparator.comparing(Address::getAddressId));
        if(optionalAddress.isPresent()){
            return optionalAddress.get().getAddressId()+1;
        }
        return 1;
    }
}
