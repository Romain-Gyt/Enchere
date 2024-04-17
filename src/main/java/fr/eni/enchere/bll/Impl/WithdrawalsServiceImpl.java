package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.WithdrawalsService;
import fr.eni.enchere.bo.Withdrawals;
import fr.eni.enchere.dal.WithdrawalsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalsServiceImpl implements WithdrawalsService {

    private final WithdrawalsDAO withdrawalsDAO;

    @Autowired
    public WithdrawalsServiceImpl(WithdrawalsDAO withdrawalsDAO) {
        this.withdrawalsDAO = withdrawalsDAO;
    }

    @Override
    public Withdrawals getWithdrawalsById(int id) {
        return withdrawalsDAO.getWithdrawalsById(id);
    }

    @Override
    public Withdrawals getWithdrawalsByArticleId(int articleId) {
        return withdrawalsDAO.getWithdrawalsByArticleId(articleId);
    }


    @Override
    public void updateWithdrawals(Withdrawals withdrawals) {
        withdrawalsDAO.updateWithdrawals(withdrawals);
    }

    @Override
    public void createWithdrawals(Withdrawals withdrawals) {
        withdrawalsDAO.createWithdrawals(withdrawals);
    }
}
