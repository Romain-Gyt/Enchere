package fr.eni.enchere.dal.Impl;

import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
/******** Declaration ********/
private static final String SELECT_BY_ID ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator,disabled\n" +
                                            "FROM users\n" +
                                            "WHERE user_id = :id;";
private static final String SELECT_BY_USERNAME ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator,disabled\n" +
                                                "FROM users\n" +
                                                "WHERE username = :username;";
private static final String SELECT_ALL ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator,disabled\n" +
                                            "FROM users\n" +
                                            "WHERE administrator = 0;";

private static final String INSERT_USER = "INSERT INTO users (username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator,disabled) " +
                                            "VALUES (:username, :last_name, :first_name, :email, :phone, :street, :postal_code, :city, :password, :credit, :administrator,0);";

    private static final String UPDATE_USER = "UPDATE users " +
            "SET username = :username, last_name = :last_name, first_name = :first_name, email = :email, phone = :phone, street = :street, postal_code = :postal_code, city = :city, password = :password, credit = :credit, administrator = :administrator " +
            "WHERE user_id = :id;";

    private static final String UPDATE_CREDIT_USER = "UPDATE users " +
            "SET credit = :credit " +
            "WHERE user_id = :id;";

private static final String DISABLE_USER = "UPDATE users SET disabled = 1 WHERE user_id = :id;";
private static final String ENABLE_USER = "UPDATE users SET disabled = 0 WHERE user_id = :id;";

private static final String LOAD_ACTIVE_ACCOUNT = "SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator,disabled\n" +
                                                    "FROM users\n" +
                                                    "WHERE  disabled = 0\n" +
                                                    "AND administrator = 0;";

private static final String LOAD_DISABLED_ACCOUNT = "SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator,disabled\n" +
                                                    "FROM users\n" +
                                                    "WHERE  disabled = 1;";

    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = :id;";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;


    /******** Constructor ********/
    public UserDaoImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }


    /******** Methods ********/
    @Override
    public User read(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                new UserRowMapper()
        );

    }

    @Override
    public User read(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", username);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_USERNAME,
                namedParameters,
                new UserRowMapper()
        );
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public List<User> loadActiveAccount() {
            return jdbcTemplate.query(
                LOAD_ACTIVE_ACCOUNT,
                new UserRowMapper()
        );
    }

    @Override
    public List<User> loadDisabledAccount() {
       return jdbcTemplate.query(
                LOAD_DISABLED_ACCOUNT,
                new UserRowMapper()
        );
    }

    @Override
    public User update(User user) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", user.getIdUser());
        namedParameters.addValue("username", user.getPseudo());
        namedParameters.addValue("last_name", user.getLastName());
        namedParameters.addValue("first_name", user.getFirstName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("phone", user.getPhoneNumber());
        namedParameters.addValue("street", user.getStreet());
        namedParameters.addValue("postal_code", user.getZipCode());
        namedParameters.addValue("city", user.getCity());
        namedParameters.addValue("credit", user.getCredit());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("administrator", user.isAdmin());
        namedParameterJdbcTemplate.update(UPDATE_USER, namedParameters);
        return user;
    }

    @Override
    public void updateCredit(User user, int credit){
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", user.getIdUser());
        namedParameters.addValue("credit", credit);
        namedParameterJdbcTemplate.update(UPDATE_CREDIT_USER, namedParameters);
    }

    @Override
    public void create(User user) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", user.getPseudo());
        namedParameters.addValue("last_name", user.getLastName());
        namedParameters.addValue("first_name", user.getFirstName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("phone", user.getPhoneNumber());
        namedParameters.addValue("street", user.getStreet());
        namedParameters.addValue("postal_code", user.getZipCode());
        namedParameters.addValue("city", user.getCity());
        namedParameters.addValue("credit", user.getCredit());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("administrator", user.isAdmin());
        namedParameterJdbcTemplate.update(INSERT_USER, namedParameters);
    }

    public void delete(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        namedParameterJdbcTemplate.update(DELETE_USER, namedParameters);
    }

    @Override
    public void disable(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        namedParameterJdbcTemplate.update(DISABLE_USER, namedParameters);
    }
    public void enable(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        namedParameterJdbcTemplate.update(ENABLE_USER, namedParameters);
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