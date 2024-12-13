package org.example.DataParser;

import java.io.IOException;
import java.util.Map;

public interface WeatherDataParser {

    String parseWeatherData(String location) throws IOException;

    Map<String, WeatherInfo> getWeatherData(String weatherData) throws IOException;
}
