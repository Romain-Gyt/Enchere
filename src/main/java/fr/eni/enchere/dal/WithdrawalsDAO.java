package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Withdrawals;

public interface WithdrawalsDAO {
    Withdrawals getWithdrawalsById(int id);
}
