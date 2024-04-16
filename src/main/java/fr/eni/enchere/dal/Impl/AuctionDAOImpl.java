package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionDAOImpl implements AuctionDAO {

    private JdbcTemplate jdbcTemplate;


    public AuctionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Auction> getAllAuctions(int itemId) {
        String sql = "SELECT *, IFNULL((SELECT bid_amount FROM bids WHERE item_id = sold_items.item_id ORDER BY bid_amount DESC LIMIT 1), initial_price) AS bid_amount FROM sold_items";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Auction auction = new Auction();
            auction.setUserId(rs.getInt("user_id"));
            auction.setItemId(rs.getInt("item_id"));
            auction.setBidDate(rs.getDate("bid_date"));
            auction.setBidAmount(rs.getInt("bid_amount"));
            return auction;
        });
    }
}
