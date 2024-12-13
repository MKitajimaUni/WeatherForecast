package org.example.DataParser;

import org.example.WeatherCode;

public interface WeatherInfo {

    public double getHumidity();

    public double getTemperature();

    public WeatherCode getWeatherCode();

    public int getRainProbability();

}
