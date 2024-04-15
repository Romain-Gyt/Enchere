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
    public List<Auction> getAllAuctions() {
        String sql = "SELECT *, IFNULL(\n" +
                "    (SELECT bid_amount FROM bids WHERE item_id = sold_items.item_id ORDER BY bid_amount DESC LIMIT 1),\n" +
                "    initial_price\n" +
                ") AS bid_amount,\n" +
                "(SELECT bid_date FROM bids WHERE item_id = sold_items.item_id ORDER BY bid_amount DESC LIMIT 1) AS bid_date\n" +
                "FROM sold_items";
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
