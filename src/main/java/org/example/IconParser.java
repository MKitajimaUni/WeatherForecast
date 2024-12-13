package org.example;

import javax.swing.*;

public class IconParser {
    public IconParser() {
    }

    public static Icon parseIcon(WeatherCode weatherCode) {

        return switch (weatherCode) {
            case CLEAR -> new ImageIcon("48b265b-weather_icon_small_ic_clear3x.jpeg");
            case CLOUDY -> new ImageIcon("4042728-weather_icon_small_ic_cloudy3x.jpeg");
            case RAIN -> new ImageIcon("aab8713-weather_icon_small_ic_rain3x.jpeg");
            default -> null;
        };
    }
}
