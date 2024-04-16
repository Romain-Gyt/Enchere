package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User loadUserById(long id);
    User loadUserByPseudo(String pseudo);
    List<User> loadAll();
    void createUser(User user,Map<String,String> passwords);
    User udpadeUser(User user, User userSession, Map<String,String> password);
    void deleteUser(Long id);
    void disableUser(Long id);
    void enableUser(Long id);
}
