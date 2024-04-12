package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

public interface BidService {

    Bid getBidById(long id);
    void insertBidAmountById(Long userId, int id, int bid_amount);
}
