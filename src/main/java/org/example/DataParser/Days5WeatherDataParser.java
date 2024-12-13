package org.example.DataParser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.WCodeVal;
import org.example.WeatherCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Days5WeatherDataParser implements WeatherDataParser {
    Map<String, WeatherInfo> weatherMap;

    @Override
    public String parseWeatherData(String location) throws IOException {
        String apiKey =  APIKey.getAPIKey();
        URL url = new URL("https://api.tomorrow.io/v4/timelines?location=" + location + "&fields=temperature&fields=weatherCode&fields=precipitationProbability&timesteps=1d&timezone=auto&apikey=" + apiKey);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    @Override
    public Map<String, WeatherInfo> getWeatherData(String weatherData) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(weatherData);
            JsonNode intervals = root.at("/data/timelines/0/intervals");

            weatherMap = new LinkedHashMap<>();

            for (int i = 0; i < 5; i++) {
                JsonNode interval = intervals.get(i);
                String startTime = interval.get("startTime").asText();

                ZonedDateTime date = ZonedDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
                String time = date.format(DateTimeFormatter.ofPattern("dd/MM"));

                JsonNode values = interval.get("values");
                int rainProb = values.get("precipitationProbability").asInt();
                double temperature = values.get("temperature").asDouble();
                int weatherCode = values.get("weatherCode").asInt();

                Weather5dInfo weatherInfo = new Weather5dInfo(rainProb, temperature, WCodeVal.convertCode(weatherCode));

                weatherMap.put(time, weatherInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherMap;
    }
}
