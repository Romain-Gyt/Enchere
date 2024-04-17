package fr.eni.enchere.dal;

import fr.eni.enchere.bo.User;

import java.util.List;

public interface DeletedAccount {
    void insertDeleteAccount(User user);
    List<User> selectAllDeletedAccount();
}

