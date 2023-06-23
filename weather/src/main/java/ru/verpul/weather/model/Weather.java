package ru.verpul.weather.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Weather {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime created;

    private List<WeatherDayInterval> dayIntervals;
    private List<WeatherShortIntervals> shortIntervals;
    private List<WeatherLongIntervals> longIntervals;

    @Getter
    private static class WeatherData {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime start;

        private Map<String, Object> temperature;
        private Map<String, Object> wind;
        private Map<String, Double> precipitation;
    }

    @Getter
    public static class WeatherDayInterval extends WeatherData {
        private List<String> sixHourSymbols;
    }

    @Getter
    public static class WeatherShortIntervals extends WeatherData {
        private Map<String, String> symbolCode;
        private Map<String, Object> feelsLike;
    }

    @Getter
    public static class WeatherLongIntervals extends WeatherData {
        private Map<String, String> symbolCode;
    }
}
