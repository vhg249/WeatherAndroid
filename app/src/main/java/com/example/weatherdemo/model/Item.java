package com.example.weatherdemo.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id, image;
    private String city, weather;
    private double temperature, high, low;

    public Item() {
    }

    public Item(int id, int temperature, int high, int low, int image, String city, String weather) {
        this.id = id;
        this.temperature = temperature;
        this.high = high;
        this.low = low;
        this.image = image;
        this.city = city;
        this.weather = weather;
    }

    public Item(int temperature, double high, double low, int image, String city, String weather) {
        this.temperature = temperature;
        this.high = high;
        this.low = low;
        this.image = image;
        this.city = city;
        this.weather = weather;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
