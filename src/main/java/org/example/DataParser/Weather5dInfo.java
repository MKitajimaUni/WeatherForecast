package org.example.DataParser;

import org.example.WeatherCode;

public class Weather5dInfo implements WeatherInfo {
    private int rainProbability;
    private  double temperature;
    private WeatherCode weatherCode;

    public Weather5dInfo(int rainProbability, double temperature, WeatherCode weatherCode) {
        this.rainProbability = rainProbability;
        this.temperature = temperature;
        this.weatherCode = weatherCode;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public double getHumidity() {
        return Double.NaN;
    }
}
