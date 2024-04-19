package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Auction;
import java.util.List;

public interface AuctionDAO {
    List<Auction> getAuctionsByArticleId(int itemId);
    List<Auction> getAllAuctions();
    List<Auction> getBestAuctionForAllArticle();
    Auction read(long id);
    void create(Long userId, int id, int bidAmount);
    void deleteAuction(int userID);
}

