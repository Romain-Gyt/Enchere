package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.ContextService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UserDao;
import org.springframework.stereotype.Service;

@Service
public class ContextServiceImpl implements ContextService {
    private UserDao userDAO;

    public ContextServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username) {
        return userDAO.read(username);
    }
}
