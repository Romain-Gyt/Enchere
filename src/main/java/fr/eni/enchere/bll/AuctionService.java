package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.bo.Withdrawals;

import java.util.List;

public interface AuctionService {
    List<Auction> getAllAuctions();

    Auction getAuctionById(long id);

    void insertBidAmountById(Long userId, int id, int bid_amount);

}
