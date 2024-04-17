package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.DeletedAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DeletedAccountImpl implements DeletedAccount
{
    public static final String INSERT_DELETE_ACCOUNT = "INSERT INTO deleted_account (user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator, disabled) " +
            "VALUES (:user_id, :username, :last_name, :first_name, :email, :phone, :street, :postal_code, :city, :password, :credit, :administrator, :disabled);";


    public static final String SELECT_ALL_DELETED_ACCOUNT = "SELECT * FROM deleted_account;";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public DeletedAccountImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertDeleteAccount(User user) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", user.getIdUser());
        namedParameters.addValue("username", user.getPseudo());
        namedParameters.addValue("last_name", user.getLastName());
        namedParameters.addValue("first_name", user.getFirstName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("phone", user.getPhoneNumber());
        namedParameters.addValue("street", user.getStreet());
        namedParameters.addValue("postal_code", user.getZipCode());
        namedParameters.addValue("city", user.getCity());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("credit", user.getCredit());
        namedParameters.addValue("administrator", user.isAdmin());
        namedParameters.addValue("disabled", user.isDisabled());
        namedParameterJdbcTemplate.update(INSERT_DELETE_ACCOUNT, namedParameters);
    }

    @Override
    public List<User> selectAllDeletedAccount() {
        return jdbcTemplate.query(SELECT_ALL_DELETED_ACCOUNT, new UserRowMapper());
    }

    class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setIdUser(resultSet.getLong("user_id"));
            user.setPseudo(resultSet.getString("username"));
            user.setLastName(resultSet.getString("last_name"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phone"));
            user.setStreet(resultSet.getString("street"));
            user.setZipCode(resultSet.getString("postal_code"));
            user.setCity(resultSet.getString("city"));
            user.setPassword(resultSet.getString("password"));
            user.setCredit(resultSet.getInt("credit"));
            user.setAdmin(resultSet.getBoolean("administrator"));
            user.setDisabled(resultSet.getBoolean("disabled"));
            return user;
        }
    }
}
