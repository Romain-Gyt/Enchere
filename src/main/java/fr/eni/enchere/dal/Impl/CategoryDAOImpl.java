package fr.eni.enchere.dal.Impl;

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

    private static final String SELECT_CAT_BY_ID = "SELECT category_id,label " +
                                                    "FROM categories" +
                                                    " WHERE category_id=:id";

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
    public Category getCategoryById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_CAT_BY_ID,params,new CategoryRowMapper());
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
