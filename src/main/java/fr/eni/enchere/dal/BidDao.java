package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Bid;

public interface BidDao {
    Bid read(long id);
    void create(Long userId, int id, int bid_amount);
}
