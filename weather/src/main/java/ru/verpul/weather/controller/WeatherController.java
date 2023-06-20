package ru.verpul.weather.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @GetMapping
    public void getMurmanskWeatherForNow() {

    }

    @GetMapping("/today")
    public void getMurmanskWeatherForToday() {

    }

    @GetMapping("/week")
    public void getMurmanskWeatherForWeek() {

    }

    @PostMapping("/{location}")
    public void getLocationWeatherForNow(@RequestBody String location) {

    }
}
