package fr.eni.enchere.dal;

import fr.eni.enchere.bo.User;

import java.util.List;

public interface UserDao {
    User read(long id);
    User read(String email);
    List<User> findAll();
    List<User> loadActiveAccount();
    List<User> loadDisabledAccount();
    User update(User user);
    void create(User user);
    void delete(long id);
    void updateCredit(User user, int credit);
    void disable(long id);
    void enable(long id);
}
