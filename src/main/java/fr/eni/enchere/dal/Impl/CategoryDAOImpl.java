package fr.eni.enchere.dal.impl;

import fr.eni.enchere.bo.Category;
import fr.eni.enchere.dal.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

   /******** Declaration *********/
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_ALL = "SELECT category_id,label " +
                                            "FROM categories";
    private static final String SELECT_BY_ID = "SELECT category_id,label " +
            "FROM categories " +
            "WHERE category_id = :id " +
            "OR :id IS NULL;";

    /********* CONSTRUCTOR *******/
    public CategoryDAOImpl (
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SELECT_ALL, new CategoryRowMapper());
    }

    @Override
    public List<Category> getCategoryById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.query(
                SELECT_BY_ID,
                namedParameters,
                new CategoryRowMapper()
        );
    }

    class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getLong("category_id"));
            category.setLabel(rs.getString("label"));
            return category;
        }
    }
}
