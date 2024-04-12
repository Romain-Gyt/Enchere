package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.BidService;
import fr.eni.enchere.bo.Bid;
import fr.eni.enchere.dal.BidDao;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {

    private BidDao bidDao;

    public BidServiceImpl(BidDao bidDao) {
        this.bidDao = bidDao;
    }

    @Override
    public Bid getBidById(long id) {
        return bidDao.read(id);
    }

    @Override
    public void insertBidAmountById(Long userId, int id, int bid_amount) {
        bidDao.create(userId, id, bid_amount);
    }
}
