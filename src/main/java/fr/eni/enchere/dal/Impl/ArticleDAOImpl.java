package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Category;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

   /******** Declaration ********/
   private static final String SELECT_ALL = "SELECT se.item_id,se.item_name,se.description,se.start_auction_date,se.end_auction_date,se.initial_price,se.sale_price,\n" +
           "        cat.category_id as cat_id,cat.label,\n" +
           "        u.user_id as id_user,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator\n" +
           "       FROM sold_items se\n" +
           "       INNER JOIN categories cat ON se.category_id = cat.category_id\n" +
           "       INNER JOIN users u ON se.user_id = u.user_id\n" +
           "      ORDER BY se.end_auction_date;";


    private static final String SELECT_BY_NAME_AND_CATEGORY = "SELECT se.item_id,se.item_name,se.description,se.start_auction_date,"
            + "se.end_auction_date,se.initial_price,se.sale_price,"
            + "cat.category_id as cat_id,cat.label,"
            + "u.user_id as id_user,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator "
        + "FROM sold_items se "
        + "INNER JOIN categories cat ON se.category_id = cat.category_id "
        + "INNER JOIN users u ON se.user_id = u.user_id "
        + "WHERE end_auction_date > CURDATE() AND (cat.category_id = :category_id OR :category_id IS NULL) AND se.item_name LIKE :item_name;";

    private static final String SELECT_OPENED_AUCTION ="SELECT se.item_id,se.item_name,se.description,se.start_auction_date,se.end_auction_date,se.initial_price,se.sale_price,\n" +
            "        cat.category_id as cat_id,cat.label,\n" +
            "        u.user_id as id_user,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator\n" +
            "       FROM sold_items se\n" +
            "       INNER JOIN categories cat ON se.category_id = cat.category_id\n" +
            "       INNER JOIN users u ON se.user_id = u.user_id\n" +
            "     WHERE se.end_auction_date > CURDATE();";

    public static final String SELECT_BY_BUYING_CURRENT_AUCTIONS = "SELECT se.item_id,se.item_name,se.description,se.start_auction_date,se.end_auction_date,se.initial_price,se.sale_price,\n" +
            " cat.category_id as cat_id,cat.label,\n" +
            " u.user_id as id_user,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator\n" +
        " FROM sold_items se\n" +
        " INNER JOIN categories cat ON se.category_id = cat.category_id\n" +
        " INNER JOIN users u ON se.user_id = u.user_id\n" +
        " WHERE end_auction_date > CURDATE()\n" +
        " AND (se.item_name LIKE :item_name OR :item_name IS NULL)\n" +
        " AND se.user_id = :user_id\n" +
        " AND (cat.category_id = :category_id  OR :category_id IS NULL);";

    public static final String SELECT_BY_BUYING_CLOSED_AUCTIONS = "SELECT se.item_id,se.item_name,se.description,se.start_auction_date,se.end_auction_date,se.initial_price,se.sale_price,\n" +
            " cat.category_id as cat_id,cat.label,\n" +
            " u.user_id as id_user,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator\n" +
        " FROM sold_items se\n" +
        " INNER JOIN categories cat ON se.category_id = cat.category_id\n" +
        " INNER JOIN users u ON se.user_id = u.user_id\n" +
        " WHERE end_auction_date < CURDATE()\n" +
        " AND (se.item_name LIKE :item_name OR :item_name IS NULL)\n" +
        " AND se.user_id = :user_id\n" +
        " AND (cat.category_id = :category_id  OR :category_id IS NULL)" +
        " AND se.sale_price IS NOT NULL;";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /******** Constructor ********/
    public ArticleDAOImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Article> getAllArticles() {
        return jdbcTemplate.query(SELECT_ALL, new ArticleRowMapper());
    }


    public List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("category_id", categoryId);
        namedParameters.addValue("item_name", nameArticle);
        return namedParameterJdbcTemplate.query(SELECT_BY_NAME_AND_CATEGORY, namedParameters, new ArticleRowMapper());
    }

    public List<Article> getArticlesByOpenedBuying() {
        return jdbcTemplate.query(SELECT_OPENED_AUCTION, new ArticleRowMapper());
    }
    @Override
    public List<Article> getArticlesByCurrentBuying(String nameArticle, Long categoryId, Long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("category_id", categoryId);
        namedParameters.addValue("item_name", nameArticle);
        namedParameters.addValue("user_id", userId);
        System.out.println(categoryId +nameArticle +userId );
        return namedParameterJdbcTemplate.query(SELECT_BY_BUYING_CURRENT_AUCTIONS, namedParameters, new ArticleRowMapper());
    }

    @Override
    public List<Article> getArticlesByClosedBuying(String nameArticle, Long categoryId, Long userId) {
       MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("category_id", categoryId);
        namedParameters.addValue("item_name", nameArticle);
        namedParameters.addValue("user_id", userId);
        return namedParameterJdbcTemplate.query(SELECT_BY_BUYING_CLOSED_AUCTIONS, namedParameters, new ArticleRowMapper());
    }


    class ArticleRowMapper implements RowMapper<Article> {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setItemId(rs.getInt("item_id"));
            article.setItemName(rs.getString("item_name"));
            article.setDescription(rs.getString("description"));
            article.setStartAuctionDate(rs.getDate("start_auction_date"));
            article.setEndAuctionDate(rs.getDate("end_auction_date"));
            article.setInitialPrice(rs.getInt("initial_price"));
            article.setSalePrice(rs.getInt("sale_price"));
            article.updateStatus();
            Category category = new Category();
            category.setCategoryId(rs.getLong("cat_id"));
            category.setLabel(rs.getString("label"));
            article.setCategory(category);
            User user = new User();
            user.setIdUser(rs.getLong("id_user"));
            user.setPseudo(rs.getString("username"));
            user.setLastName(rs.getString("last_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phone"));
            user.setStreet(rs.getString("street"));
            user.setZipCode(rs.getString("postal_code"));
            user.setCity(rs.getString("city"));
            user.setCredit(rs.getInt("credit"));
            user.setAdmin(rs.getBoolean("administrator"));
            article.setUser(user);
            return article;
        }
    }
}
