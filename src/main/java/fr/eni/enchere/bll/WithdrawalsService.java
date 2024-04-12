package fr.eni.enchere.bll;

import fr.eni.enchere.bo.SoldItem;
import fr.eni.enchere.bo.Withdrawals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalsService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT street, postal_code, city FROM withdrawals WHERE item_id = :item_id";

    public Withdrawals getWithdrawalsById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        try{
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,
                    namedParameters,
                    (resultSet, rowNum) -> {
                        Withdrawals withdrawals = new Withdrawals();
                        withdrawals.setStreet(resultSet.getString("street"));
                        withdrawals.setPostal_code(resultSet.getString("postal_code"));
                        withdrawals.setCity(resultSet.getString("city"));
                        return withdrawals;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
