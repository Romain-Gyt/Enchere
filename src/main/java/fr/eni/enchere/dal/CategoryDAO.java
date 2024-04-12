package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();
}
