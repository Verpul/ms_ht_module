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
    public String getMyCityWeatherForNow() {
        return weatherComponent.makeMyCityWeatherMessageForNow();
    }

    @GetMapping("/today")
    public void getMyCityWeatherForToday() {

    }

    @GetMapping("/week")
    public void getMyCityWeatherForWeek() {

    }

    @PostMapping("/{location}")
    public void getLocationWeatherForNow(@RequestBody String location) {

    }
}
