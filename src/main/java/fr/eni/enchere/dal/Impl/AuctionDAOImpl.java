package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AuctionDAOImpl implements AuctionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Auction> getAuctionsByItemId(int itemId) {
        String sql = "SELECT * FROM bids WHERE item_id = ?";
        return jdbcTemplate.query(sql, new Object[]{itemId}, (rs, rowNum) -> {
            Auction auction = new Auction();
            auction.setUserId(rs.getInt("user_id"));
            auction.setItemId(rs.getInt("item_id"));
            auction.setBidDate(rs.getDate("bid_date"));
            auction.setBidAmount(rs.getInt("bid_amount"));
            return auction;
        });
    }

    @Override
    public int getBidAmountByItemId(int itemId) {
        String sql = "SELECT bid_amount FROM bids WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, Integer.class);
    }

    @Override
    public List<Auction> getAllAuctions() {
        String sql = "SELECT * FROM bids";
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
