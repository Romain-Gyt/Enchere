package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UserDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    /********** Declaration *********/
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

/********** Constructor *********/
    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /************ Methods *********/

    @Override
    public User udpadeUser(User user, User userSession, Map<String,String> password) {
        user = checkObject(user, userSession);
        user.setPassword(setPassword(user, userSession, password).getPassword());
        return userDao.update(user);
    }

    @Override
    public void deleteUser(Long id) {
         userDao.delete(id);
    }

    private User checkObject(User user,User userSession) {
        user.setIdUser(userSession.getIdUser());
        user.setCredit(userSession.getCredit());
        user.setAdmin(userSession.isAdmin());
        if (user.getPseudo() == null || user.getPseudo().isEmpty()) {
            user.setPseudo(userSession.getPseudo());
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            user.setLastName(userSession.getLastName());
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            user.setFirstName(userSession.getFirstName());
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            user.setEmail(userSession.getEmail());
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userSession.getPhoneNumber());
        }
        if (user.getStreet() == null || user.getStreet().isEmpty()) {
            user.setStreet(userSession.getStreet());
        }
        if (user.getZipCode() == null || user.getZipCode().isEmpty()) {
            user.setZipCode(userSession.getZipCode());
        }
        if (user.getCity() == null || user.getCity().isEmpty()) {
            user.setCity(userSession.getCity());
        }
        return user;
    }

    private User setPassword(User user,User userSession, Map<String, String> password) {
        if(password.size() < 3){
            user.setPassword(userSession.getPassword());
            return user;
        }
        if (password.get("newPassword") != null && !password.get("newPassword").isEmpty()) {
            if (checkPassword(password.get("currentPassword"), userSession.getPassword())) {
                if (password.get("newPassword").equals(password.get("confirmPassword"))) {
                    user.setPassword(encodePassword(password.get("newPassword")));
                } else{
                    user.setPassword(userSession.getPassword());
                }
            } else{
                user.setPassword(userSession.getPassword());
            }
        } else {
            user.setPassword(userSession.getPassword());
        }
        return user;
    }

    private boolean checkPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
