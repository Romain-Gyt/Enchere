package fr.eni.enchere.bo;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Article {

    /******** Attributs ********/
    private int itemId;
    private String itemName;
    private String description;
    private Date startAuctionDate;
    private Date endAuctionDate;
    private int initialPrice;
    private Integer salePrice;
    private int userId;
    private String Status;
    private Integer latestBidAmount;
    private String image;

    private Withdrawals withdrawals;
    private Category category;
    private User user;
    private List<Auction> auctions;


    /******** Constructor ********/
    public Article() {
    }


    /******** Methods ********/
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLatestBidAmount() {
        return latestBidAmount;
    }

    public void setLatestBidAmount(Integer latestBidAmount) {
        this.latestBidAmount = latestBidAmount;
    }

    public Withdrawals getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(Withdrawals withdrawals) {
        this.withdrawals = withdrawals;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }

    public void removeAuction(Auction auction) {
        this.auctions.remove(auction);
    }

    /******** toString ********/
    @Override
    public String toString() {
        return "Article{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", startAuctionDate=" + startAuctionDate +
                ", endAuctionDate=" + endAuctionDate +
                ", initialPrice=" + initialPrice +
                ", salePrice=" + salePrice +
                ", userId=" + userId +
                ", category=" + category +
                ", user=" + user +
                ", withdrawals=" + withdrawals +
                '}';
    }

    /******** equals ********/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return itemId == article.itemId && initialPrice == article.initialPrice && userId == article.userId && Objects.equals(itemName, article.itemName) && Objects.equals(description, article.description) && Objects.equals(startAuctionDate, article.startAuctionDate) && Objects.equals(endAuctionDate, article.endAuctionDate) && Objects.equals(salePrice, article.salePrice) && Objects.equals(category, article.category) && Objects.equals(user, article.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, description, startAuctionDate, endAuctionDate, initialPrice, salePrice, userId, category, user);
    }


}
