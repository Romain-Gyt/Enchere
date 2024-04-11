package fr.eni.enchere.dal.impl;

import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.UsersDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsersDAOImpl implements UsersDAO {
    private static final String INSERT_INTO = "INSERT INTO users (username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator) VALUES (:username, :last_name, :first_name, :email, :phone, :street, :postal_code, :city, :password, :credit, :administrator);";
    private static final String SELECT_COUNT_BY_EMAIL = "SELECT COUNT(*) AS userEmail FROM users WHERE email = :email;";
    private static final String SELECT_COUNT_BY_USERNAME = "SELECT COUNT(*) AS userUsername FROM users WHERE username = :username;";
    private static final String SELECT_BY_USERNAME = "SELECT user_id, username, last_name, first_name, email, phone, street, postal_code, city, password, credit, administrator FROM users WHERE username = :username;";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private PasswordEncoder passwordEncoder;

    public UsersDAOImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            PasswordEncoder passwordEncoder
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.passwordEncoder = passwordEncoder;
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

    public long create(User user) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", user.getPseudo());
        namedParameters.addValue("last_name", user.getLastName());
        namedParameters.addValue("first_name", user.getFirstName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("phone", user.getPhoneNumber());
        namedParameters.addValue("street", user.getStreet());
        namedParameters.addValue("postal_code", user.getZipCode());
        namedParameters.addValue("city", user.getCity());
        namedParameters.addValue("password", passwordEncoder.encode(user.getPassword()));
        namedParameters.addValue("credit", user.getCredit());
        namedParameters.addValue("administrator", user.isAdmin());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_INTO, namedParameters, keyHolder);
        Number key = keyHolder.getKey();
        return key.intValue();
    }

    @Override
    public long findByEmail(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", email);
        return namedParameterJdbcTemplate.queryForObject(SELECT_COUNT_BY_EMAIL, namedParameters, Long.class);
    }

    @Override
    public long findByUsername(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", username);
        return namedParameterJdbcTemplate.queryForObject(SELECT_COUNT_BY_USERNAME, namedParameters, Long.class);
    }

    class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setIdUser(rs.getLong("user_id"));
            user.setPseudo(rs.getString("username"));
            user.setLastName(rs.getString("last_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setEmail(rs.getString("email"));
            user.setEmail(rs.getString("phone"));
            user.setEmail(rs.getString("street"));
            user.setEmail(rs.getString("postal_code"));
            user.setEmail(rs.getString("city"));
            user.setEmail(rs.getString("password"));
            user.setEmail(rs.getString("credit"));
            user.setAdmin(rs.getBoolean("administrator"));

            return user;
        }
    }
}
