package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Withdrawals;
import fr.eni.enchere.dal.WithdrawalsDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class WithdrawalsDAOImpl implements WithdrawalsDAO {

    /******** Declaration ********/
    private static final String SELECT_BY_ID = "SELECT street, postal_code, city FROM withdrawals WHERE item_id = :item_id";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /******** Constructor ********/
    public WithdrawalsDAOImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Withdrawals getWithdrawalsById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        try{
            return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new WithdrawalsRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    class WithdrawalsRowMapper implements RowMapper<Withdrawals> {
        @Override
        public Withdrawals mapRow(ResultSet rs, int rowNum) throws SQLException {
            Withdrawals withdrawals = new Withdrawals();
            withdrawals.setStreet(rs.getString("street"));
            withdrawals.setPostal_code(rs.getString("postal_code"));
            withdrawals.setCity(rs.getString("city"));
            return withdrawals;
        }
    }
}