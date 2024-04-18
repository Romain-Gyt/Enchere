package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Withdrawals;

public interface WithdrawalsService {
    public abstract Withdrawals getWithdrawalsById(int id);

    void updateWithdrawals(Withdrawals withdrawals);

    void createWithdrawals(Withdrawals withdrawals);

    Withdrawals getWithdrawalsByArticleId(int itemId);
}
