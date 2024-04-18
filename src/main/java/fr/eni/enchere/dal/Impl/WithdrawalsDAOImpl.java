package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.Withdrawals;
import fr.eni.enchere.dal.WithdrawalsDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class WithdrawalsDAOImpl implements WithdrawalsDAO {

    /******** Declaration ********/
    private static final String SELECT_BY_ID = "SELECT street, postal_code, city FROM withdrawals WHERE item_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM withdrawals WHERE item_id = :item_id";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /******** Constructor ********/
    public WithdrawalsDAOImpl(JdbcTemplate jdbcTemplate,
                              NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Withdrawals getWithdrawalsByArticleId(int articleId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[]{articleId}, new WithdrawalsRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public Withdrawals getWithdrawalsById(int withdrawalsId) {
        try {
            String sql = "SELECT street, postal_code, city FROM withdrawals WHERE withdrawals_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{withdrawalsId}, new WithdrawalsRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateWithdrawals(Withdrawals withdrawals) {
        String sql = "UPDATE withdrawals SET street = ?, postal_code = ?, city = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, withdrawals.getStreet(), withdrawals.getPostal_code(), withdrawals.getCity(), withdrawals.getItem_id());
    }

    @Override
    public void createWithdrawals(Withdrawals withdrawals) {
        String sql = "INSERT INTO withdrawals (item_id, street, postal_code, city) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, withdrawals.getItem_id(), withdrawals.getStreet(), withdrawals.getPostal_code(), withdrawals.getCity());
    }

    @Override
    public void deleteWithdrawals(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        namedParameterJdbcTemplate.update(DELETE_BY_ID, namedParameters);
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
