package fr.eni.enchere.dal;

import fr.eni.enchere.bo.User;

import java.util.List;

public interface UserDao {
    User read(long id);
    User read(String email);
    List<User> findAll();
    User update(User user);
    void delete(long id);
}
