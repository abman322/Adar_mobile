package com.example.adar;

public class HouseModel {

    private String city, country, price, imageUrl, description, phone;

    public HouseModel(){

    }

    public HouseModel(String city, String country, String price, String imageUrl, String description, String phone){
        this.city = city;
        this.country = country;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.phone = phone;
    }

    public String getCity(){
        return city;
    }
    public String getCountry(){
        return country;
    }
    public String getPrice(){
        return price;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public String getPhone() {
        return phone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
