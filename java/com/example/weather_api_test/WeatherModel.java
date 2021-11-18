package com.example.weather_api_test;

public class WeatherModel {
    private final String city_name,temperature,aqi,wtype,sunrise,sunset,humidity,pressure,windspeed,realfeel;

    public WeatherModel(String cn,String temp,String aqi,String wtype,String sunrise,String sunset,String humidity,String pressure,String windspeed,String realfeel){
        this.city_name=cn;
        this.temperature=temp;
        this.aqi=aqi;
        this.wtype=wtype;
        this.sunrise=sunrise;
        this.sunset=sunset;
        this.humidity=humidity;
        this.pressure=pressure;
        this.windspeed=windspeed;
        this.realfeel=realfeel;

    }
    public String getCity_name(){
        return this.city_name;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getAqi() {
        return aqi;
    }

    public String getWtype() {
        return wtype;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getRealfeel() {
        return realfeel;
    }

    public String getWindspeed() {
        return windspeed;
    }
}
