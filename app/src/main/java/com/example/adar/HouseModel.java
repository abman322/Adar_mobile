package com.example.adar;

public class HouseModel {

    private String city, country, price, imageUrl, description;

    public HouseModel(){

    }

    public HouseModel(String city, String country, String price, String imageUrl, String description){
        this.city = city;
        this.country = country;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
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
}
