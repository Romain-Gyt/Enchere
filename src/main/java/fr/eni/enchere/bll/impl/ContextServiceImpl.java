package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.ContextService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UsersDAO;
import org.springframework.stereotype.Service;

@Service
public class ContextServiceImpl implements ContextService {
    private UsersDAO usersDAO;

    public ContextServiceImpl(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    public User load(String username) {
        return usersDAO.read(username);
    }
}
