package com.example.baoanj.hw9;

/**
 * Created by baoanj on 2016/11/30.
 */
public class Weather {
    public String date;
    private String weather_description;
    private String temperature;

    public Weather(String date, String weather_description, String temperature) {
        this.date = date;
        this.weather_description = weather_description;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public String getTemperature() {
        return temperature;
    }
}
