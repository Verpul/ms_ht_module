package ru.verpul.weather.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime created;

    private List<WeatherDayInterval> dayIntervals;
    private List<WeatherShortIntervals> shortIntervals;

    @Getter
    private static class WeatherData {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime start;

        private Map<String, Object> temperature;
        private Map<String, Object> wind;
        private Map<String, Double> precipitation;
    }

    @Getter
    private static class WeatherDayInterval extends WeatherData{
        private List<String> sixHourSymbols;
    }

    @Getter
    private static class WeatherShortIntervals extends WeatherData{
        private Map<String, String> symbolCode;
        private Map<String, String> feelsLike;
    }
}
