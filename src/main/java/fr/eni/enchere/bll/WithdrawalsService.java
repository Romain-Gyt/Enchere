package fr.eni.enchere.bll;

import fr.eni.enchere.bo.SoldItem;
import fr.eni.enchere.bo.Withdrawals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

public interface WithdrawalsService {
    Withdrawals getWithdrawalsById(int id);
}
