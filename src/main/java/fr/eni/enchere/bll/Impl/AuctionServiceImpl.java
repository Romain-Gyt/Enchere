package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.AuctionService;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    /********** DECLARATION *********/
    private AuctionDAO auctionDAO;
  
  /*********CONSTRUCTOR **********/
  public AuctionServiceImpl(AuctionDAO auctionDAO) {
    this.auctionDAO = auctionDAO;
  }

//    @Override
//    public List<Auction> getAllAuctions(int itemId) {
//        return auctionDAO.getAllAuctions(itemId);
//    }

    @Override
    public void deleteAuction(int userID) {
       auctionDAO.deleteAuction(userID);
    }

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

    @Override
    public List<Auction> getAuctionsByArticleId(int articleId) {
        return auctionDAO.getAuctionsByArticleId(articleId);
    }

}

