package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UsersDAO;
import fr.eni.enchere.exception.BusinessException;
import org.springframework.stereotype.Service;

//@Service
//public class UserServiceImpl implements UserService {
//
//    private UsersDAO userDAO;
//
//    public UserServiceImpl(
//            UsersDAO userDAO
//    ) {
//        this.userDAO = userDAO;
//    }
//
//    @Override
//    public void createUser(User user) {
//        BusinessException businessException = new BusinessException();
//        boolean isValid = true;
//        if(isValid){
//            isValid &= doesUserEmailExist(user, businessException);
//            isValid &= doesUsernameExist(user, businessException);
//        }
//        if (isValid) {
//            userDAO.create(user);
//        } else {
//            throw businessException;
//        }
//    }
//
//    public boolean doesUserEmailExist(User user, BusinessException businessException) {
//        String email = user.getEmail();
//        long existingUser = userDAO.findByEmail(email);
//        if (existingUser >= 1) {
//            businessException.addKey("L'email existe déja");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean doesUsernameExist(User user, BusinessException businessException) {
//        String username = user.getPseudo();
//        long existingUser = userDAO.findByUsername(username);
//        if (existingUser >= 1) {
//            businessException.addKey("Le pseudo est déja utilisé");
//            return false;
//        }
//        return true;
//    }
//}
