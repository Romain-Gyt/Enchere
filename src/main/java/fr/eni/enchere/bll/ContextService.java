package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;

import java.util.List;

public interface ContextService {
    User loadUserById(long id);
    User loadUserByUsername(String email);
    List<User> loadAll();
}
