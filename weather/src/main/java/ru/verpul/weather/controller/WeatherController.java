package ru.verpul.weather.controller;

import org.springframework.web.bind.annotation.*;
import ru.verpul.weather.component.WeatherComponent;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherComponent weatherComponent;

    public WeatherController(WeatherComponent weatherComponent) {
        this.weatherComponent = weatherComponent;
    }

    @GetMapping
    public String getWeatherForToday() {
        return weatherComponent.makeMyCityWeatherMessageForNow();
    }

    @GetMapping("/tomorrow")
    public void getMyCityWeatherForTomorrow() {

    }

    @GetMapping("/week")
    public void getWeatherForWeek() {

    }
}
