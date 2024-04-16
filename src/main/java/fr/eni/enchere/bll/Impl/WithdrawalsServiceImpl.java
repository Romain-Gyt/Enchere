package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.WithdrawalsService;
import fr.eni.enchere.bo.Withdrawals;
import fr.eni.enchere.dal.WithdrawalsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalsServiceImpl implements WithdrawalsService {
    @Autowired
    private WithdrawalsDAO withdrawalsDAO;

    @Override
    public Withdrawals getWithdrawalsById(int id) {
        return withdrawalsDAO.getWithdrawalsById(id);
    }
}
