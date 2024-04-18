package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.DeletedAccount;
import fr.eni.enchere.dal.UserDao;
import fr.eni.enchere.exception.RegisterCode;
import fr.eni.enchere.exception.RegisterException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * Implémentation du service utilisateur.
 * Cette classe implémente les méthodes définies dans l'interface UserService.
 */
@Service
public class UserServiceImpl implements UserService {
    /********** Declaration *********/
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private DeletedAccount deletedAccount;

/********** Constructor *********/

    public UserServiceImpl(UserDao userDao,
                           PasswordEncoder passwordEncoder,
                            DeletedAccount deletedAccount
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.deletedAccount = deletedAccount;
    }

    /************ Methods *********/
    @Override
    public User loadUserById(long id) {
        return  userDao.read(id);
    }
    @Override
    public User loadUserByPseudo(String pseudo) {
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
        RegisterException registerException = new RegisterException();
        user = checkObject(user, userSession,password,registerException);
//        user.setPassword(setPassword(user, userSession, password,registerException).getPassword());
        if(registerException.getKeys().size() > 0){
            throw registerException;
        }
        return userDao.update(user);
    }
    @Override
    public void updateUserCredit(User user, int credit){
        userDao.updateCredit(user, credit);
    }
    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public void insertDeleteAccount(User user) {
        deletedAccount.insertDeleteAccount(user);

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

    /**
     * Vérifie et met à jour les informations d'un utilisateur en fonction des informations fournies.
     * Cette méthode vérifie également la validité des informations de l'utilisateur et du mot de passe.
     *
     * @param user L'utilisateur à vérifier et à mettre à jour.
     * @param userSession L'utilisateur actuellement connecté.
     * @param password Un Map contenant les informations de mot de passe fournies.
     * @param registerException Une exception personnalisée pour gérer les erreurs d'enregistrement.
     * @return L'utilisateur mis à jour si les informations fournies sont valides, sinon l'utilisateur original.
     */
    private User checkObject(User user,
                             User userSession,
                             Map<String, String> password,
                             RegisterException registerException
    ) {
        Boolean isValid = isUserNull(user,registerException);
        if(isValid){
            user.setIdUser(userSession.getIdUser());
            user.setCredit(userSession.getCredit());
            user.setAdmin(userSession.isAdmin());
            if (user.getPseudo() == null || user.getPseudo().isEmpty()) {
                user.setPseudo(userSession.getPseudo());
            } else{
                isValid &=isPeudoValid(user.getPseudo(),registerException);
            }
            if (user.getLastName() == null || user.getLastName().isEmpty()) {
                user.setLastName(userSession.getLastName());
            } else{
                isValid &=isLastNameValid(user.getLastName(),registerException);
            }
            if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
                user.setFirstName(userSession.getFirstName());
            }else{
                isValid &=isFirstNameValid(user.getFirstName(),registerException);
            }
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                user.setEmail(userSession.getEmail());
            }else{
                isValid &=isEmailValid(user.getEmail(),registerException);
            }
            if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
                user.setPhoneNumber(userSession.getPhoneNumber());
            } else{
                isValid &=isPhoneNumberValid(user.getPhoneNumber(),registerException);
            }
            if (user.getStreet() == null || user.getStreet().isEmpty()) {
                user.setStreet(userSession.getStreet());
            } else{
                isValid &=isStreetValid(user.getStreet(),registerException);
            }
            if (user.getZipCode() == null || user.getZipCode().isEmpty()) {
                user.setZipCode(userSession.getZipCode());
            } else{
                isValid &=isZipCodeValid(user.getZipCode(),registerException);
            }
            if (user.getCity() == null || user.getCity().isEmpty()) {
                user.setCity(userSession.getCity());
            } else{
                isValid &=isCityValid(user.getCity(),registerException);
            }
            if(user.getPassword() ==  null || user.getPassword().isEmpty()){
                user.setPassword(userSession.getPassword());
            } else{
                isValid &= isPasswordValid(user.getPassword(),registerException);
                isValid &= isPasswordValid(password.get("newPassword"),registerException);
                isValid &= isPasswordValid(password.get("confirmPassword"),registerException);
                if (checkPassword(password.get("currentPassword"),user.getPassword())){
                    isValid &= true;
                } else{
                    registerException.addKey(RegisterCode.CURRENT_PASSWORD_UNKNOWN);
                }
                if(password.get("newPassword").equals(password.get("confirmPassword"))){
                    isValid &= true;
                } else{
                    registerException.addKey(RegisterCode.PASSWORD_CONFIRMATION);
                }
            }
        }
        if(isValid){
            user.setPassword(encodePassword(password.get("newPassword")));
            return user;
        }
        return user;
    }
    /**
     * Vérifie si le mot de passe fourni correspond au mot de passe encodé stocké.
     *
     * @param password Le mot de passe en clair à vérifier.
     * @param encodedPassword Le mot de passe encodé stocké à comparer.
     * @return true si le mot de passe correspond au mot de passe encodé, false sinon.
     */
    private boolean checkPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
 