package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(long id);
    List<Category> getCategoryByIdFilter(Long id);
}
