package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;

import java.util.List;

public interface AdminService {
    void deleteUser(Long id);
    void disableUser(Long id);
    void enableUser(Long id);
    void insertDeleteAccount(User user);
    List<User> loadAllDeletedAccount();
    List<User> loadActiveAccount();
    List<User> loadDisabledAccount();
}
