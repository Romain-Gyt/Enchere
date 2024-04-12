package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Auction;
import java.util.List;

public interface AuctionDAO {
    List<Auction> getAllAuctions();
}

