package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.AuctionService;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionDAO auctionDAO;

    @Override
    public List<Auction> getAllAuctions() {
        return auctionDAO.getAllAuctions();
    }

    @Override
    public Auction getAuctionById(long id) {
        return auctionDAO.read(id);
    }

    @Override
    public void insertBidAmountById(Long userId, int id, int bid_amount) {
        auctionDAO.create(userId, id, bid_amount);
    }

}

