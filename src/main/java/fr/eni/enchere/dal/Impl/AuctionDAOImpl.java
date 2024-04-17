package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionDAOImpl implements AuctionDAO {

    private static  final String DELETE_AUCTION = "DELETE FROM sold_items WHERE user_id = :user_id";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public AuctionDAOImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    @Override
    public void deleteAuction(int userID) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userID);
        namedParameterJdbcTemplate.update(DELETE_AUCTION, namedParameters);
    }
}
