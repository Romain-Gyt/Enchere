package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;

import java.util.Map;

public interface UserService {

    User udpadeUser(User user, User userSession, Map<String,String> password);
    void deleteUser(Long id);
}
