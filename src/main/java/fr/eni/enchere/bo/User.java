package fr.eni.enchere.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    /******* Attributs *******/

    private int idUser;
    private String pseudo;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String street;
    private String zipCode;
    private String city;
    private String password;
    private int credit;
    private boolean admin = false;

    List<Article> soldArticles = new ArrayList<Article>();
    List<Article> boughtArticles = new ArrayList<Article>();
    List<Auction> auctions = new ArrayList<Auction>();

    /************** Constructor *********/

    public User() {
    }

    public User(int idUser,
                String pseudo,
                String lastName,
                String firstName,
                String email,
                String phoneNumber,
                String street,
                String zipCode,
                String city,
                String password,
                int credit,
                boolean admin
    ) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
    }

    /********** Accesseur *********/

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Article> getSoldArticles() {
        return soldArticles;
    }

    public void addSoldArticles (Article article){
        this.soldArticles.add(article);
    }

    public void removeSoldArticle(Article article){
        this.soldArticles.remove(article);
    }

    public void setSoldArticles(List<Article> soldArticles) {
        this.soldArticles = soldArticles;
    }

    public List<Article> getBoughtArticles() {
        return boughtArticles;
    }

    public void addBoughtArticle(Article article){
        this.boughtArticles.add(article);
    }

    public void removeBoughtArticle(Article article){
        this.boughtArticles.remove(article);
    }

    public void setBoughtArticles(List<Article> boughtArticles) {
        this.boughtArticles = boughtArticles;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void addAuction(Auction auction){
        this.auctions.add(auction);
    }

    public void removeAuction(Auction auction){
        this.auctions.remove(auction);
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    /********** Methode **********/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return credit == user.credit && admin == user.admin && Objects.equals(idUser, user.idUser) && Objects.equals(pseudo, user.pseudo) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(street, user.street) && Objects.equals(zipCode, user.zipCode) && Objects.equals(city, user.city) && Objects.equals(password, user.password) && Objects.equals(soldArticles, user.soldArticles) && Objects.equals(boughtArticles, user.boughtArticles) && Objects.equals(auctions, user.auctions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, pseudo, lastName, firstName, email, phoneNumber, street, zipCode, city, password, credit, admin, soldArticles, boughtArticles, auctions);
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", pseudo='" + pseudo + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                ", soldArticles=" + soldArticles +
                ", boughtArticles=" + boughtArticles +
                ", auctions=" + auctions +
                '}';
    }
}