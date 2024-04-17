package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.AdminService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.DeletedAccount;
import fr.eni.enchere.dal.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private UserDao userDao;
    private DeletedAccount deletedAccount;


    public AdminServiceImpl(UserDao userDao, DeletedAccount deletedAccount) {
        this.userDao = userDao;
        this.deletedAccount = deletedAccount;
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public void disableUser(Long id) {
        userDao.disable(id);
    }

    @Override
    public void enableUser(Long id) {
        userDao.enable(id);
    }

    @Override
    public void insertDeleteAccount(User user) {
        deletedAccount.insertDeleteAccount(user);
    }

    @Override
    public List<User> loadAllDeletedAccount() {
        return deletedAccount.selectAllDeletedAccount();
    }

    @Override
    public List<User> loadActiveAccount() {
        return userDao.loadActiveAccount();
    }

    @Override
    public List<User> loadDisabledAccount() {
        return userDao.loadDisabledAccount();
    }
}

