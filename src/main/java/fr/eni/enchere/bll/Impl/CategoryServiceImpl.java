package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Category;
import fr.eni.enchere.dal.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    /******** Declaration ********/

    private CategoryDAO categoryDAO;

    /********* CONSTRUCTOR ********/
    public CategoryServiceImpl(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
}