package fr.eni.enchere.dal.impl;

import fr.eni.enchere.bo.Category;
import fr.eni.enchere.dal.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setLabel(rs.getString("label"));
            return category;
        });
    }
}
