package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.ContextService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextServiceImpl implements ContextService{
    /********* DECLARATION *********/
    private UserDao userDao;

    /********* CONSTRUCTOR *********/
    public ContextServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loadUserById(long id) {
        return  userDao.read(id);
    }

    /********* METHODS *********/
    @Override
    public User loadUserByUsername(String email) {
        return userDao.read(email);
    }

    @Override
    public List<User> loadAll() {
        return userDao.findAll();
    }
}
