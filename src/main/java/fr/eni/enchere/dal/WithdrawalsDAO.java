package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Withdrawals;

public interface WithdrawalsDAO {
    Withdrawals getWithdrawalsById(int id);

    Withdrawals getWithdrawalsByArticleId(int articleId);

    void updateWithdrawals(Withdrawals withdrawals);

    void createWithdrawals(Withdrawals withdrawals);
    void deleteWithdrawals(int id);
}
