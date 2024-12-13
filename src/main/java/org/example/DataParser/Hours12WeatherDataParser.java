package org.example.DataParser;

import java.net.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.WCodeVal;


public class Hours12WeatherDataParser implements WeatherDataParser {
    private Map<String, WeatherInfo> weatherMap;

    public String parseWeatherData(String location) throws IOException {
        String apiKey =  APIKey.getAPIKey();
        URL url = new URL("https://api.tomorrow.io/v4/timelines?location=" + location + "&fields=temperature&fields=humidity&fields=weatherCode&timesteps=1h&timezone=auto&apikey=" + apiKey);
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

    public Map<String, WeatherInfo> getWeatherData(String weatherData) {

        try {
            // Create an ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Parse the JSON string into a JsonNode
            JsonNode root = mapper.readTree(weatherData);

            // Navigate to the "intervals" array
            JsonNode intervals = root.at("/data/timelines/0/intervals");

            // Create a map to store the result
            weatherMap = new LinkedHashMap<>();

            // Iterate through intervals
            for (int i = 0; i < 12; i++) {
                JsonNode interval = intervals.get(i);
                String startTime = interval.get("startTime").asText();

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
                ZonedDateTime date = ZonedDateTime.parse(startTime, inputFormatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH");
                String time = date.format(outputFormatter);

                JsonNode values = interval.get("values");
                double humidity = values.get("humidity").asDouble();
                double temperature = values.get("temperature").asDouble();
                int weatherCode = values.get("weatherCode").asInt();

                // Create WeatherInfo object
                Weather12hInfo weatherInfo = new Weather12hInfo(WCodeVal.convertCode(weatherCode), temperature, humidity);

                // Put the data into the map
                weatherMap.put(time, weatherInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherMap;
    }
}
