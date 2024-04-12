package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BidService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT bid_amount FROM bids WHERE item_id = :item_id";
    private static final String INSERT_BID_AMOUNT_BY_ID = "INSERT INTO bids (user_id, item_id, bid_date, bid_amount) VALUES (:user_id, :item_id, :bid_date, :bid_amount);";

    public Bid getBidById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        try{
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,
                    namedParameters,
                    (resultSet, rowNum) -> {
                        Bid bid = new Bid();
                        bid.setBid_amount(resultSet.getInt("bid_amount"));
                        return bid;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void UpdateBidAmountById(int userId, int id, int bid_amount) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("bid_amount", bid_amount);
        namedParameters.addValue("item_id", id);
        namedParameters.addValue("user_id", userId);
        namedParameters.addValue("bid_date", new Date());
        namedParameterJdbcTemplate.update(INSERT_BID_AMOUNT_BY_ID, namedParameters);
    }
}
