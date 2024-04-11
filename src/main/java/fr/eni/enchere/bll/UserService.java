package fr.eni.enchere.bll;

import fr.eni.enchere.bo.User;
import fr.eni.enchere.bo.Withdrawals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

//public interface UserService {
//    void createUser(User user);
//}
@Service
public class UserService {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT username, street, postal_code, city, credit FROM users WHERE user_id = :id";

    public User getUserById(int id) {
        System.out.println(id);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID,
                namedParameters,
                (resultSet, rowNum) -> {
                    User user = new User();
                    user.setPseudo(resultSet.getString("username"));
                    user.setStreet(resultSet.getString("street"));
                    user.setZipCode(resultSet.getString("postal_code"));
                    user.setCity(resultSet.getString("city"));
                    user.setCredit(resultSet.getInt("credit"));
                    return user;
                }
        );
    }
}
