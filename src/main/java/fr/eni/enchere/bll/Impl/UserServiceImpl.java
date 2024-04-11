package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UserDao;
import fr.eni.enchere.exception.RegisterCode;
import fr.eni.enchere.exception.RegisterException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public User loadUserById(long id) {
        return  userDao.read(id);
    }

    @Override
    public User loadUserByPseudo(String pseudo) {
        System.out.println("test user");
        return userDao.read(pseudo);
    }

    @Override
    public List<User> loadAll() {
        return userDao.findAll();
    }

    @Override
    public void createUser(User user,Map<String,String> password) {
        RegisterException registerException = new RegisterException();
       boolean isValid = isUserNull(user,registerException);
        if(isValid){
            isValid = isPeudoValid(user.getPseudo(),registerException);
            isValid &= isLastNameValid(user.getLastName(),registerException);
            isValid &= isFirstNameValid(user.getFirstName(),registerException);
            isValid &= isEmailValid(user.getEmail(),registerException);
            isValid &= isPhoneNumberValid(user.getPhoneNumber(),registerException);
            isValid &= isStreetValid(user.getStreet(),registerException);
            isValid &= isZipCodeValid(user.getZipCode(),registerException);
            isValid &= isCityValid(user.getCity(),registerException);
            isValid &= isPasswordValid(password.get("password"),registerException);
            isValid &= isPasswordConfirmationValid(password,registerException);
        }
        if(isValid){
            user.setPassword(encodePassword(password.get("password")));
            user.setCredit(500);
            user.setAdmin(false);
            userDao.create(user);
        } else {
            registerException.addKey(RegisterCode.REGISTER_ERROR);
            throw registerException;
        }
    }

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

    private boolean isUserNull(User user,RegisterException registerException) {
        if (user == null) {
            registerException.addKey(RegisterCode.USER_IS_NULL);
            return false;
        }
        return true;
    }

    private boolean isPeudoValid(String pseudo,RegisterException registerException) {
        if (pseudo == null || pseudo.isEmpty()) {
            registerException.addKey(RegisterCode.PSEUDO_IS_EMPTY);
            return false;
        }
        if(pseudo.length() < 3 || pseudo.length() > 20){
            registerException.addKey(RegisterCode.PSEUDO_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isLastNameValid(String lastName,RegisterException registerException) {
        if (lastName == null || lastName.isEmpty()) {
            registerException.addKey(RegisterCode.LAST_NAME_IS_EMPTY);
            return false;
        }
        if(lastName.length() < 3 || lastName.length() > 30){
            registerException.addKey(RegisterCode.LAST_NAME_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isFirstNameValid(String firstName,RegisterException registerException) {
        if (firstName == null || firstName.isEmpty()) {
            registerException.addKey(RegisterCode.FIRST_NAME_IS_EMPTY);
            return false;
        }
        if(firstName.length() < 3 || firstName.length() > 30){
            registerException.addKey(RegisterCode.FIRST_NAME_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email,RegisterException registerException) {
        if (email == null || email.isEmpty()) {
            registerException.addKey(RegisterCode.EMAIL_IS_EMPTY);
            return false;
        }
        if(!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            registerException.addKey(RegisterCode.EMAIL_FORMAT);
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberValid (String phoneNumber,RegisterException registerException) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            registerException.addKey(RegisterCode.PHONE_NUMBER_IS_EMPTY);
            return false;
        }
        if(!phoneNumber.matches("^0[1-9]([-. ]?[0-9]{2}){4}$")){
            registerException.addKey(RegisterCode.PHONE_NUMBER_FORMAT);
            return false;
        }
        return true;
    }

    private boolean isStreetValid(String street,RegisterException registerException) {
        if (street == null || street.isEmpty()) {
            registerException.addKey(RegisterCode.STREET_IS_EMPTY);
            return false;
        }
        if(street.length() < 3 || street.length() > 30){
            registerException.addKey(RegisterCode.STREET_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isZipCodeValid(String zipCode,RegisterException registerException) {
        if (zipCode == null || zipCode.isEmpty()) {
            registerException.addKey(RegisterCode.POSTAL_CODE_IS_EMPTY);
            return false;
        }
        if(!zipCode.matches("^[0-9]{5}$")){
            registerException.addKey(RegisterCode.POSTAL_CODE_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isCityValid(String city,RegisterException registerException) {
        if (city == null || city.isEmpty()) {
            registerException.addKey(RegisterCode.CITY_IS_EMPTY);
            return false;
        }
        if(city.length() < 3 || city.length() > 30){
            registerException.addKey(RegisterCode.CITY_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password,RegisterException registerException) {
        if (password == null || password.isEmpty()) {
            registerException.addKey(RegisterCode.PASSWORD_IS_EMPTY);
            return false;
        }
        if(password.length() < 8 || password.length() > 30){
            registerException.addKey(RegisterCode.PASSWORD_LENGTH);
            return false;
        }
        return true;
    }

    private boolean isPasswordConfirmationValid(Map<String,String> passwords,RegisterException registerException) {
        if (passwords.get("confirmPassword") == null || passwords.get("confirmPassword").isEmpty()) {
            registerException.addKey(RegisterCode.PASSWORD_CONFIRMATION_IS_EMPTY);
            return false;
        }
        if(!passwords.get("password").equals(passwords.get("confirmPassword"))){
            registerException.addKey(RegisterCode.PASSWORD_CONFIRMATION);
            return false;
        }
        return true;
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
