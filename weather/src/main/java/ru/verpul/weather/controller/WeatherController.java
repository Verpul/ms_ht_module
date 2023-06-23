package ru.verpul.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.verpul.weather.component.WeatherMessageToTelegramComponent;
import ru.verpul.weather.data.WeatherPeriod;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherMessageToTelegramComponent weatherMessageToTelegramComponent;

    @GetMapping
    public String getWeatherForToday() {
        return weatherMessageToTelegramComponent.makeMyCityWeatherMessage(WeatherPeriod.TODAY);
    }

    @GetMapping("/days")
    public String getMyCityWeatherForTomorrow() {
        return weatherMessageToTelegramComponent.makeMyCityWeatherMessage(WeatherPeriod.THREE_DAYS);
    }

    @GetMapping("/week")
    public String getWeatherForWeek() {
        return weatherMessageToTelegramComponent.makeMyCityWeatherMessage(WeatherPeriod.WEEK);
    }
}
