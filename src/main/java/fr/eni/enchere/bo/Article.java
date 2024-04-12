package fr.eni.enchere.bo;

import org.eclipse.angus.mail.imap.protocol.UIDSet;

import java.sql.Date;

public class Article {
    private int itemId;
    private String itemName;
    private String description;
    private Date startAuctionDate;
    private Date endAuctionDate;
    private int initialPrice;
    private Integer salePrice; // Peut être nul si l'enchère n'est pas encore terminée
    private int userId;
    private Category category;
    private User user;


    public Article() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartAuctionDate() {
        return startAuctionDate;
    }

    public void setStartAuctionDate(Date startAuctionDate) {
        this.startAuctionDate = startAuctionDate;
    }

    public Date getEndAuctionDate() {
        return endAuctionDate;
    }

    public void setEndAuctionDate(Date endAuctionDate) {
        this.endAuctionDate = endAuctionDate;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(int initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
