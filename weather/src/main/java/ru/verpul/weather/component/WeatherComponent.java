package ru.verpul.weather.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.verpul.weather.model.Weather;

@Slf4j
@Component
public class WeatherComponent {
    @Value("${api.myCityWeatherURL}")
    String myCityWeatherURL;

    private final RestTemplate restTemplate;

    private static Weather weather;

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
            weather = restTemplate.getForObject(myCityWeatherURL, Weather.class);
        } catch (Exception e) {
            log.error("Weather loading error: " + e.getMessage(), e);
        }
    }

    public Weather getWeather() {
        return weather;
    }
}
