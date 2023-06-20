package ru.verpul.weather.component;

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

    public WeatherComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 * * * * ?")
    private void loadMyCityWeatherData() {
        try {
            WeatherDTO weatherDTO = restTemplate.getForObject(myCityWeatherURL, WeatherDTO.class);
            System.out.println(weatherDTO.getCreated());
        } catch (Exception e) {
            log.error("Weather loading error: " + e.getMessage(), e);
        }
    }
}
