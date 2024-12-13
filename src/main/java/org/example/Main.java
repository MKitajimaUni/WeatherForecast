package org.example;

import org.example.DataParser.*;

class Main {
    public static void main(String[] args) throws Exception {
        WeatherDataParser parser = new Days5WeatherDataParser();
        System.out.println(parser.getWeatherData(parser.parseWeatherData("Salzburg")));
        UI ui = new UI();
        ui.start();
    }
}