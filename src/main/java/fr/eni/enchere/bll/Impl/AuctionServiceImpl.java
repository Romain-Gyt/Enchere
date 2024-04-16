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
    public List<Auction> getAllAuctions(int itemId) {
        return auctionDAO.getAllAuctions(itemId);
    }

    @Override
    public void deleteAuction(int userID) {
        auctionDAO.deleteAuction(userID);
    }

}