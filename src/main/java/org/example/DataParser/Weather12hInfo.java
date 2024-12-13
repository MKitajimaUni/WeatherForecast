package org.example.DataParser;

import org.example.WeatherCode;

public class Weather12hInfo implements WeatherInfo {
    private final double humidity;
    private final double temperature;
    private final WeatherCode weatherCode;

    public Weather12hInfo(WeatherCode weatherCode, double temperature, double humidity) {
        this.humidity = humidity;
        this.temperature = temperature;
        this.weatherCode = weatherCode;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public int getRainProbability() {
        return -1;
    }

    @Override
    public String toString() {
        return (int) humidity + " " + (int) temperature + " " + weatherCode;
    }
}
