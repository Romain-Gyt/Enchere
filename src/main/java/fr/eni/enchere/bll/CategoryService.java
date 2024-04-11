package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT label FROM categories WHERE category_id = :id";

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setLabel(rs.getString("label"));
            return category;
        });
    }

    public String getCategorieById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, String.class);

//        String sql = "SELECT label FROM categories WHERE id = :id";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> {
//            Category category = new Category();
//            category.setLabel(rs.getString("label"));
//            return category;
//        });
    }
}
