package ru.verpul.weather.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.verpul.weather.DTO.WeatherDTO;

@Slf4j
@Component
public class WeatherComponent {
    @Value("${api.myCityWeatherURL}")
    String myCityWeatherURL;

    private final RestTemplate restTemplate;

    private static WeatherDTO weatherDTO;

    public WeatherComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void loadMyCityWeatherDataOnStartup() {
        this.loadMyCityWeatherData();
    }

    @Scheduled(cron = "30 1 */1 * * *")
    private void loadMyCityWeatherData() {
        try {
            weatherDTO= restTemplate.getForObject(myCityWeatherURL, WeatherDTO.class);
        } catch (Exception e) {
            log.error("Weather loading error: " + e.getMessage(), e);
        }
    }

    public String makeMyCityWeatherMessageForNow() {
        WeatherDTO.WeatherShortIntervals weatherShortIntervals = weatherDTO.getShortIntervals().get(0);
        int temperature = objectToIntWithRound(weatherShortIntervals.getTemperature().get("value"));
        int windSpeed = objectToIntWithRound(weatherShortIntervals.getWind().get("speed"));
        int windGust = objectToIntWithRound(weatherShortIntervals.getWind().get("gust"));
        double precipitation = weatherShortIntervals.getPrecipitation().get("value");

        String message = "Температура: " + temperature + "°C\n";
        if (precipitation != 0.0) {
            message += "Осадки: " + precipitation + " мм\n";
        };
        message += "Ветер: " + "\n\t";
        message += "Скорость: " + windSpeed + " м/с\n\t";
        message += "Порывы до : " + windGust + " м/с";

        return message;
    }

    private int objectToIntWithRound(Object number) {
        return (int) (Math.round((double) number));
    }
}
