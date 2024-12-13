package org.example;

public class WCodeVal {

    public static WeatherCode convertCode(int code) {
        switch (code) {
            case 1000 -> {
                return WeatherCode.CLEAR;
            }
            case 1100 -> {
                return WeatherCode.MOSTLY_CLEAR;
            }
            case 1101 -> {
                return WeatherCode.PARTLY_CLOUDY;
            }
            case 1102 -> {
                return WeatherCode.MOSTLY_CLOUDY;
            }
            case 1001 -> {
                return WeatherCode.CLOUDY;
            }
            case 4000 -> {
                return WeatherCode.DRIZZLE;
            }
            case 4200 -> {
                return WeatherCode.LIGHT_RAIN;
            }
            case 4001 -> {
                return WeatherCode.RAIN;
            }
            case 4201 -> {
                return WeatherCode.HEAVY_RAIN;
            }
            case 2100 ->{
                return WeatherCode.LIGHT_FOG;
            }
            case 2000 -> {
                return WeatherCode.FOG;
            }
            case 5001 -> {
                return WeatherCode.FLURRIES;
            }
            case 5100 -> {
                return WeatherCode.LIGHT_SNOW;
            }
            case 5000 -> {
                return WeatherCode.SNOW;
            }
            case 5101 -> {
                return WeatherCode.HEAVY_SNOW;
            }
            default -> {
                return WeatherCode.UNKNOWN;
            }
        }
    }
}
