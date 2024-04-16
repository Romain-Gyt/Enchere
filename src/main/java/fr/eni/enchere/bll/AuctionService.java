package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Auction;
import java.util.List;

public interface AuctionService {
    List<Auction> getAllAuctions(int itemId);
    void deleteAuction(int userID);
}
