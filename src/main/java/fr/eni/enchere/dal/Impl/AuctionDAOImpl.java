package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.bo.Category;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuctionDAOImpl implements AuctionDAO {
/**********DECLARATION**********/

    private static final String SELECT_ALL_AUCTIONS = "SELECT b.user_id AS bid_user,b.item_id AS bid_item,b.bid_date,b.bid_amount,\n" +
        "        se.item_id AS sold_item_id,se.item_name,se.description,se.start_auction_date,se.end_auction_date,se.initial_price,se.sale_price,\n" +
        "        u.user_id AS userId,u.username,u.last_name,u.first_name,u.email,u.phone,u.street,u.postal_code,u.city,u.credit,u.administrator,\n" +
        "        cat.category_id AS cat_id,cat.label\n" +
        "    FROM bids b\n" +
        "    INNER JOIN sold_items se ON b.item_id = se.item_id\n" +
        "    INNER JOIN users u ON b.user_id = u.user_id\n" +
        "    INNER JOIN categories cat ON se.category_id = cat.category_id\n" +
        "WHERE b.item_id = :item_id;";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

/**********CONSTRUCTOR**********/
    public AuctionDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
  /**********METHODS**********/
    @Override
    public List<Auction> getAllAuctions(int itemId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", itemId);
        return namedParameterJdbcTemplate.query(SELECT_ALL_AUCTIONS,namedParameters,new AuctionRowMapper());

    }

    class AuctionRowMapper implements RowMapper<Auction> {
        @Override
        public Auction mapRow(ResultSet resultSet, int i) throws SQLException {
            Auction auction = new Auction();
            auction.setUserId(resultSet.getInt("bid_user"));
            auction.setItemId(resultSet.getInt("bid_item"));
            auction.setBidDate(resultSet.getDate("bid_date"));
            auction.setBidAmount(resultSet.getInt("bid_amount"));
            Article article = new Article();
            article.setItemId(resultSet.getInt("sold_item_id"));
            article.setItemName(resultSet.getString("item_name"));
            article.setDescription(resultSet.getString("description"));
            article.setStartAuctionDate(resultSet.getDate("start_auction_date"));
            article.setEndAuctionDate(resultSet.getDate("end_auction_date"));
            article.setInitialPrice(resultSet.getInt("initial_price"));
            article.setSalePrice(resultSet.getInt("sale_price"));
            Category category = new Category();
            category.setCategoryId(resultSet.getInt("cat_id"));
            category.setLabel(resultSet.getString("label"));
            article.setCategory(category);
            auction.setArticle(article);
            User user = new User();
            user.setIdUser(resultSet.getLong("userId"));
            user.setPseudo(resultSet.getString("username"));
            user.setLastName(resultSet.getString("last_name"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phone"));
            user.setStreet(resultSet.getString("street"));
            user.setZipCode(resultSet.getString("postal_code"));
            user.setCity(resultSet.getString("city"));
            user.setCredit(resultSet.getInt("credit"));
            user.setAdmin(resultSet.getBoolean("administrator"));
            auction.setUser(user);

            return auction;
        }
    }
}
