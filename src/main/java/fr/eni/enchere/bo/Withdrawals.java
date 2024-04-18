package fr.eni.enchere.bo;

public class Withdrawals {
    private int item_id;
    private String street;
    private String postal_code;
    private String city;

    public Withdrawals() {
    }

    public Withdrawals(int item_id, String street, String postal_code, String city) {
        this.item_id = item_id;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
