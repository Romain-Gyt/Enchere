package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;

public interface ContextService {
    User load(String username);
}
