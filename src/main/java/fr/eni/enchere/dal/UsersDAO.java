package fr.eni.enchere.dal;

import fr.eni.enchere.bo.User;

public interface UsersDAO {
    User read(String username);
    long create(User user);
    long findByEmail(String email);
    long findByUsername(String username);
}
