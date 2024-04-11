package fr.eni.enchere.bll;

import fr.eni.enchere.bo.SoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class SoldItemService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Utilisation de JdbcTemplate pour exécuter des requêtes SQL

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
}
