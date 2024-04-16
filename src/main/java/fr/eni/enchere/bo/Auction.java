package fr.eni.enchere.bo;

import java.util.Date;

public class Auction {
    private int userId;
    private int itemId;
    private Date bidDate;
    private int bidAmount;
    private  Article article;
    private User user;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "userId=" + userId +
                ", itemId=" + itemId +
                ", bidDate=" + bidDate +
                ", bidAmount=" + bidAmount +
                ", article=" + article +
                '}';
    }
}
