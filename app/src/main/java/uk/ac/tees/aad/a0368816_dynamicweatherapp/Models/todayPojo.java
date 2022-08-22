package uk.ac.tees.aad.a0368816_dynamicweatherapp.Models;

public class todayPojo {
    public String datetime;
    public String description;
    public String temperature;
    public String humidity;
    public String pressure;
    public String wind;
    public String imageUrl;

    public  todayPojo()
    {

    }


    public todayPojo(String datetime, String description, String temperature, String humidity, String pressure, String wind, String imageUrl) {
        this.datetime = datetime;
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.imageUrl = imageUrl;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
