package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Auction;
import java.util.List;

public interface AuctionDAO {
    List<Auction> getAuctionsByItemId(int itemId);
    List<Auction> getAllAuctions();
    Auction read(long id);
    void create(Long userId, int id, int bidAmount);
}

