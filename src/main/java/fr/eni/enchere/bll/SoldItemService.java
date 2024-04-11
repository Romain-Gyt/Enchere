package fr.eni.enchere.bll;

import fr.eni.enchere.bo.SoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class SoldItemService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Utilisation de JdbcTemplate pour exécuter des requêtes SQL
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_BY_ID = "SELECT item_name, description, end_auction_date, initial_price, sale_price, user_id, category_id FROM sold_items WHERE item_id = :item_id";

    public List<SoldItem> getSoldItemsEnCours() {
        String sql = "SELECT * FROM sold_items WHERE end_auction_date >= CURDATE()"; // Sélection des éléments de la base de données en cours
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SoldItem item = new SoldItem();
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setDescription(rs.getString("description"));
            item.setStartAuctionDate(rs.getDate("start_auction_date"));
            item.setEndAuctionDate(rs.getDate("end_auction_date"));
            item.setInitialPrice(rs.getInt("initial_price"));
            item.setSalePrice(rs.getInt("sale_price"));
            item.setUserId(rs.getInt("user_id"));
            item.setCategoryId(rs.getInt("category_id"));
            return item;
        });
    }

    public SoldItem getSoldItemById(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("item_id", id);
        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                namedParameters,
                (resultSet, rowNum) -> {
                    SoldItem item = new SoldItem();
                    item.setItemName(resultSet.getString("item_name"));
                    item.setDescription(resultSet.getString("description"));
                    item.setEndAuctionDate(resultSet.getDate("end_auction_date"));
                    item.setInitialPrice(resultSet.getInt("initial_price"));
                    item.setSalePrice(resultSet.getInt("sale_price"));
                    item.setUserId(resultSet.getInt("user_id"));
                    item.setCategoryId(resultSet.getInt("category_id"));
                    return item;
                }
        );
    }
}