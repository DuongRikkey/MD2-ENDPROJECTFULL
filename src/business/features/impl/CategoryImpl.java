package business.features.impl;

import business.entity.Category;
import business.features.IGenericDesign;
import business.utils.IOFILE;
import business.utils.ShopConstanst;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CategoryImpl implements IGenericDesign<Category,Integer> {
    public static List<Category> categoryList= IOFILE.getListFromFile(ShopConstanst.CATEGORY_PATH);
    @Override
    public void addAndUpdate(Category category) {
        int indexCheck=findIndexbyID(category.getCategoryId());
        if(indexCheck==-1){
            categoryList.add(category);
            category.setCategoryId(getNewId());
        }
        else {
            categoryList.set(indexCheck, category);
        }
        IOFILE.writeListToFile(categoryList, ShopConstanst.CATEGORY_PATH);
    }

    @Override
    public void remove(Integer id) {
        categoryList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(CategoryImpl.categoryList, ShopConstanst.CATEGORY_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return categoryList.stream().map(Category::getCategoryId).toList().indexOf(id);
    }

    @Override
    public Integer getNewId() {
        Optional<Category> optionalCategory=categoryList.stream().max(Comparator.comparingInt(Category::getCategoryId));
        if(optionalCategory.isPresent()){
            return optionalCategory.get().getCategoryId()+1;
        }
        return 1;
    }

}
