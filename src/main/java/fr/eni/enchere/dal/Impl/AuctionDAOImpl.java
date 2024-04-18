package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class AuctionDAOImpl implements AuctionDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT user_id, bid_amount FROM bids WHERE item_id = :item_id ORDER BY bid_amount DESC LIMIT 1;";
    private static final String INSERT_BID_AMOUNT_BY_ID = "INSERT INTO bids (user_id, item_id, bid_date, bid_amount) VALUES (:user_id, :item_id, :bid_date, :bid_amount) ON DUPLICATE KEY UPDATE bid_date = :bid_date, bid_amount = :bid_amount;";

    public class AuctionRowMapper implements RowMapper<Auction> {
        @Override
        public Auction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Auction auction = new Auction();
            auction.setUserId(rs.getInt("user_id"));
            auction.setItemId(rs.getInt("item_id"));
            auction.setBidDate(rs.getDate("bid_date"));
            auction.setBidAmount(rs.getInt("bid_amount"));
            return auction;
        }
    }

    public AuctionDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Auction> getAuctionsByArticleId(int itemId) {
        String sql = "SELECT * FROM bids WHERE item_id = :item_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", itemId);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new AuctionRowMapper());
    }


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

    @Override
    public Auction read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        try{
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,
                    namedParameters,
                    (resultSet, rowNum) -> {
                        Auction auction = new Auction();
                        auction.setUserId(resultSet.getInt("user_id"));
                        auction.setBidAmount(resultSet.getInt("bid_amount"));
                        return auction;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void create(Long userId, int id, int bid_amount) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("bid_amount", bid_amount);
        namedParameters.addValue("item_id", id);
        namedParameters.addValue("user_id", userId);
        namedParameters.addValue("bid_date", new Date());
        namedParameterJdbcTemplate.update(INSERT_BID_AMOUNT_BY_ID, namedParameters);
    }
}