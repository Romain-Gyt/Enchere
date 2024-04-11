package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Auction> getAllAuctions() {
        String sql = "SELECT bids.*, sold_items.item_name, sold_items.initial_price, users.username " +
                "FROM bids " +
                "INNER JOIN sold_items ON bids.item_id = sold_items.item_id " +
                "INNER JOIN users ON bids.user_id = users.user_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Auction auction = new Auction();
            auction.setUserId(rs.getInt("user_id"));
            auction.setItemId(rs.getInt("item_id"));
            auction.setBidDate(rs.getDate("bid_date"));
            auction.setBidAmount(rs.getInt("bid_amount"));
            // Set item name from sold_items table
            auction.setItemName(rs.getString("item_name"));
            // Set initial price from sold_items table
            auction.setInitialPrice(rs.getInt("initial_price"));
            // Set username from users table
            auction.setUsername(rs.getString("username"));
            return auction;
        });
    }
    public List<Auction> getAuctionsByCategory(Long categoryId) {
        String sql = "SELECT bids.*, sold_items.item_name, sold_items.initial_price, users.username " +
                "FROM bids " +
                "INNER JOIN sold_items ON bids.item_id = sold_items.item_id " +
                "INNER JOIN users ON bids.user_id = users.user_id " +
                "WHERE sold_items.category_id = ?";
        return jdbcTemplate.query(sql, new Object[] { categoryId }, (rs, rowNum) -> {
            Auction auction = new Auction();
            auction.setUserId(rs.getInt("user_id"));
            auction.setItemId(rs.getInt("item_id"));
            auction.setBidDate(rs.getDate("bid_date"));
            auction.setBidAmount(rs.getInt("bid_amount"));
            // Set item name from sold_items table
            auction.setItemName(rs.getString("item_name"));
            // Set initial price from sold_items table
            auction.setInitialPrice(rs.getInt("initial_price"));
            // Set username from users table
            auction.setUsername(rs.getString("username"));
            return auction;
        });
    }
}