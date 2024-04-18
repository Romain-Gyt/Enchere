package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Withdrawals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

public interface WithdrawalsService {
    public abstract Withdrawals getWithdrawalsById(int id);

    void updateWithdrawals(Withdrawals withdrawals);

    void createWithdrawals(Withdrawals withdrawals);

    Withdrawals getWithdrawalsByArticleId(int itemId);
}
