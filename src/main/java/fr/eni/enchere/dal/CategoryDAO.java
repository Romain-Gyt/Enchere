package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Category;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();
    Category getCategoryById(long id);
  // regler le probleme car la categorie liste sert pour les filtrages
    List<Category> getCategoryByIdFilter(Long id);
}
