package business.features.impl;

import business.constants.RoleName;
import business.entity.Users;
import business.features.IUserImpl;
import business.utils.IOFILE;
import business.utils.InputMethod;
import business.utils.ShopConstanst;
import presentation.run.Main;

import java.util.*;

public class UsersImpl implements IUserImpl {
    public static List<Users> usersList= IOFILE.getListFromFile(ShopConstanst.USER_PATH);

    static {
//        usersList.add(new Users(1, "Duongadmin", "Admin User", "admin@example.com", "Admin123", true, "+84000000000", "Admin Address", new Date(), new Date(), RoleName.ROLE_ADMIN, false,1000));
//        usersList.add(new Users(2, "Admin123", "Admin User", "admin@example.com", "Admin123", false, "+84000000000", "Admin Address", new Date(), new Date(), RoleName.ROLE_ADMIN, false,1000));
    }
    public static Users findByUsernameAndEmail(String username, String email) {
        Optional<Users> usersOptional=usersList.stream().filter(users -> users.getUsername().equals(username) &&users.getEmail().equals(email) ).findFirst();
        return usersOptional.orElse(null);
    }

    @Override
    public Users login(Users user) {
        return usersList.stream().
                filter(u->u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())).
                findFirst().
                orElse(null);
    }

    @Override
    public void addAndUpdate(Users users) {
        int indexCheck=findIndexbyID(users.getId());
        if(indexCheck==-1){
            users.setId(getNewId());
            usersList.add(users);
        }else {
            usersList.set(indexCheck, users);
        }

        IOFILE.writeListToFile(usersList,ShopConstanst.USER_PATH);
    }

    @Override
    public void remove(Integer id) {
        usersList.remove(findIndexbyID(id));
        IOFILE.writeListToFile(usersList,ShopConstanst.USER_PATH);
    }

    @Override
    public int findIndexbyID(Integer id) {
        return usersList.stream().map(Users::getId).toList().indexOf(id);
    }


    @Override
    public Integer getNewId() {
        Optional<Users> optionalUsers=usersList.stream().max(Comparator.comparingInt(Users::getId));
        if(optionalUsers.isPresent()){
            return optionalUsers.get().getId()+1;
        }
        return 1;
    }

    }

