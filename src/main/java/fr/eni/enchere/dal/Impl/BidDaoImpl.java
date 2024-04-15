package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Bid;
import fr.eni.enchere.dal.BidDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class BidDaoImpl implements BidDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT user_id, bid_amount FROM bids WHERE item_id = :item_id ORDER BY bid_amount DESC LIMIT 1;";
    private static final String INSERT_BID_AMOUNT_BY_ID = "INSERT INTO bids (user_id, item_id, bid_date, bid_amount) VALUES (:user_id, :item_id, :bid_date, :bid_amount) ON DUPLICATE KEY UPDATE bid_date = :bid_date, bid_amount = :bid_amount;";

    public BidDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Bid read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        try{
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,
                    namedParameters,
                    (resultSet, rowNum) -> {
                        Bid bid = new Bid();
                        bid.setUser_id(resultSet.getInt("user_id"));
                        bid.setBid_amount(resultSet.getInt("bid_amount"));
                        return bid;
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
