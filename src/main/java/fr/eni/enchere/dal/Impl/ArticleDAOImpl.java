package fr.eni.enchere.dal.impl;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Article> getAllArticles() {
        String sql = "SELECT sold_items.*, users.username FROM sold_items " +
                "INNER JOIN users ON sold_items.user_id = users.user_id " +
                "WHERE sold_items.end_auction_date >= CURDATE()";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapArticle(rs));
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
        article.setUserId(rs.getInt("user_id"));
        article.setUsername(rs.getString("username"));
        article.setCategoryId(rs.getInt("category_id"));
        return article;
    }
}
