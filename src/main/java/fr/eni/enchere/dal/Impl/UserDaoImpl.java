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
private static final String SELECT_BY_ID ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator\n" +
                                            "FROM users\n" +
                                            "WHERE user_id = :id;";
private static final String SELECT_BY_USERNAME ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator\n" +
                                                "FROM users\n" +
                                                "WHERE username = :username;";
private static final String SELECT_ALL ="SELECT user_id,username,last_name,first_name,email,phone,street,postal_code,city,password,credit,administrator\n" +
                                            "FROM users;";
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
            return user;
        }
    }
}
