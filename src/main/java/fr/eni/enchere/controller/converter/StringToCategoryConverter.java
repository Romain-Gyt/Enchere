package fr.eni.enchere.controller.converter;

import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {
    private CategoryService categoryService;

    public StringToCategoryConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category convert(String id) {
        long CategoryId = Long.parseLong(id);
        return categoryService.getCategoryById(CategoryId);
    }
}
