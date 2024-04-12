package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuctionService {

    List<Auction> getAllAuctions();
}