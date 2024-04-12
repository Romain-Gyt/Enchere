package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.ContextService;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Service;

@Service
public class ContextServiceImpl implements ContextService {
    private UserDAO userDAO;

    public ContextServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username) {
        return userDAO.read(username);
    }
}
