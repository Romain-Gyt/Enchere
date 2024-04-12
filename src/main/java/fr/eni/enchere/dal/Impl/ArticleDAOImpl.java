package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
           "       WHERE end_auction_date > CURDATE();";

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

    @Override
    public List<Article> getArticlesByCategory(Long categoryId) {
        String sql = "SELECT sold_items.*, users.username, IFNULL((SELECT bid_amount FROM bids WHERE item_id = sold_items.item_id ORDER BY bid_date DESC LIMIT 1), sold_items.initial_price) AS bid_amount FROM sold_items " +
                "INNER JOIN users ON sold_items.user_id = users.user_id " +
                "WHERE sold_items.end_auction_date >= CURDATE() AND sold_items.category_id = ?";
        return jdbcTemplate.query(sql, new Object[]{categoryId}, (rs, rowNum) -> mapArticle(rs));
    }

    private Article mapArticle(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setItemId(rs.getInt("item_id"));
        article.setItemName(rs.getString("item_name"));
        article.setDescription(rs.getString("description"));
        article.setStartAuctionDate(rs.getDate("start_auction_date"));
        article.setEndAuctionDate(rs.getDate("end_auction_date"));
        article.setInitialPrice(rs.getInt("initial_price"));
        article.setSalePrice(rs.getInt("sale_price"));
        return article;
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
            return article;
        }
    }
}
