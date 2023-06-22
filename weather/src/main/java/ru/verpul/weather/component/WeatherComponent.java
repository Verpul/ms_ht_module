package ru.verpul.weather.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.verpul.weather.model.Weather;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String makeMyCityWeatherMessageForNow() {
        Map<String, String> weatherData = getShortIntervalData(weather.getShortIntervals().get(0));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


        String message = "<b>Мурманск</b>, данные на " + LocalDate.now().format(dateTimeFormatter) + "\n\n";
        message += "<b>Текущая:</b>\n";
        message += getWeatherEmoji(weatherData.get("weatherIcon")) + "\n";
        message += "Температура: " + weatherData.get("temperature") + "°C (Ощущается как " + weatherData.get("temperatureFeelsLike") + "°C)\n";
        if (!weatherData.get("precipitation").equals("0.0")) {
            message += "Осадки:" + weatherData.get("precipitation") + " мм\n";
        }
        ;
        message += "Ветер: " + weatherData.get("windSpeed") + " м/с (Порывы до " + weatherData.get("windGust") + " м/с)\n\n";
        message += "<b>Дневная:</b>\n";
        message += getDailyWeatherMessage(weather.getShortIntervals());

        return message;
    }

    private String getDailyWeatherMessage(List<Weather.WeatherShortIntervals> shortIntervals) {
        StringBuilder message = new StringBuilder();
        Map<String, String> weatherData;

        for (Weather.WeatherShortIntervals interval: shortIntervals) {
            weatherData = getShortIntervalData(interval);

            if (weatherData.get("intervalStart").equals("00:00")) break;
            message.append(weatherData.get("intervalStart")).append(", ");
            message.append(getWeatherEmoji(weatherData.get("weatherIcon"))).append(", ");
            message.append(weatherData.get("temperature")).append("°C, ");
            if (!weatherData.get("precipitation").equals("0.0"))
                message.append(weatherData.get("precipitation")).append(" мм, ");
            message.append(weatherData.get("windSpeed")).append(" м/с\n");
        }
        return message.toString();
    }

    private Map<String, String> getShortIntervalData(Weather.WeatherShortIntervals shortInterval) {
        Map<String, String> weatherData = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        weatherData.put("temperature", objectToIntWithRound(shortInterval.getTemperature().get("value")));
        weatherData.put("temperatureFeelsLike", objectToIntWithRound(shortInterval.getFeelsLike().get("value")));
        weatherData.put("windSpeed", objectToIntWithRound(shortInterval.getWind().get("speed")));
        weatherData.put("windGust", objectToIntWithRound(shortInterval.getWind().get("gust")));
        weatherData.put("precipitation", String.valueOf(shortInterval.getPrecipitation().get("value")));
        weatherData.put("weatherIcon", shortInterval.getSymbolCode().get("next1Hour"));
        weatherData.put("intervalStart", shortInterval.getStart().format(dateTimeFormatter));

        return weatherData;
    }

    private String objectToIntWithRound(Object number) {
        return String.valueOf(Math.round((double) number));
    }

    private String getWeatherEmoji(String weatherIcon) {
        Map<String, String> weatherIconWithEmoji = new HashMap<>();
        weatherIconWithEmoji.put("clearsky_day", "Безоблачно\u2600");
        weatherIconWithEmoji.put("clearsky_night", "Безоблачно\uD83C\uDF19");
        weatherIconWithEmoji.put("clearsky_polartwilight", "Безоблачно");
        weatherIconWithEmoji.put("cloudy", "Облачно\u2601");
        weatherIconWithEmoji.put("fair_day", "Ясно\uD83C\uDF24");
        weatherIconWithEmoji.put("fair_night", "Ясно");
        weatherIconWithEmoji.put("fair_polartwilight", "Ясно");
        weatherIconWithEmoji.put("fog", "Туман\uD83C\uDF2B");
        weatherIconWithEmoji.put("heavyrain", "Сильный дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("heavyrainandthunder", "Сильный дождь и гроза\u26C8");
        weatherIconWithEmoji.put("heavyrainshowersandthunder_day", "Сильный дождь и гроза\u26C8");
        weatherIconWithEmoji.put("heavyrainshowersandthunder_night", "Сильный дождь и гроза\u26C8");
        weatherIconWithEmoji.put("heavyrainshowersandthunder_polartwilight", "Сильный дождь и гроза\u26C8");
        weatherIconWithEmoji.put("heavyrainshowers_day", "Сильный дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("heavyrainshowers_night", "Сильный дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("heavyrainshowers_polartwilight", "Сильный дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("heavysleet", "Сильный мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysleetandthunder", "Сильный мокрый снег и гроза");
        weatherIconWithEmoji.put("heavysleetshowersandthunder_day", "Сильный мокрый снег и гроза");
        weatherIconWithEmoji.put("heavysleetshowersandthunder_night", "Сильный мокрый снег и гроза");
        weatherIconWithEmoji.put("heavysleetshowersandthunder_polartwilight", "Сильный мокрый снег и гроза");
        weatherIconWithEmoji.put("heavysleetshowers_day", "Сильный мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysleetshowers_night", "Сильный мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysleetshowers_polartwilight", "Сильный мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysnow", "Сильный снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysnowandthunder", "Сильный снег и гроза");
        weatherIconWithEmoji.put("heavysnowshowersandthunder_day", "Сильный снег и гроза");
        weatherIconWithEmoji.put("heavysnowshowersandthunder_night", "Сильный снег и гроза");
        weatherIconWithEmoji.put("heavysnowshowersandthunder_polartwilight", "Сильный снег и гроза");
        weatherIconWithEmoji.put("heavysnowshowers_day", "Сильный снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysnowshowers_night", "Сильный снег\uD83C\uDF28");
        weatherIconWithEmoji.put("heavysnowshowers_polartwilight", "Сильный снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightrain", "Легкий дождь\uD83C\uDF28");
        weatherIconWithEmoji.put("lightrainandthunder", "Легкий дождь и гроза\u26C8");
        weatherIconWithEmoji.put("lightrainshowersandthunder_day", "Легкий дождь и гроза\u26C8");
        weatherIconWithEmoji.put("lightrainshowersandthunder_night", "Легкий дождь и гроза\u26C8");
        weatherIconWithEmoji.put("lightrainshowersandthunder_polartwilight", "Легкий дождь и гроза\u26C8");
        weatherIconWithEmoji.put("lightrainshowers_day", "Легкий дождь\uD83C\uDF28");
        weatherIconWithEmoji.put("lightrainshowers_night", "Легкий дождь\uD83C\uDF28");
        weatherIconWithEmoji.put("lightrainshowers_polartwilight", "Легкий дождь\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsleet", "Легкий мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsleetandthunder", "Легкий мокрый снег и гроза");
        weatherIconWithEmoji.put("lightsleetshowers_day", "Легкий мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsleetshowers_night", "Легкий мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsleetshowers_polartwilight", "Легкий мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsnow", "Легкий снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsnowandthunder", "Легкий снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsnowshowers_day", "Легкий снег и гроза");
        weatherIconWithEmoji.put("lightsnowshowers_night", "Легкий снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightsnowshowers_polartwilight", "Легкий снег\uD83C\uDF28");
        weatherIconWithEmoji.put("lightssleetshowersandthunder_day", "Легкий мокрый снег и гроза");
        weatherIconWithEmoji.put("lightssleetshowersandthunder_night", "Легкий мокрый снег и гроза");
        weatherIconWithEmoji.put("lightssleetshowersandthunder_polartwilight", "Легкий мокрый снег и гроза");
        weatherIconWithEmoji.put("lightssnowshowersandthunder_day", "Легкий снег и гроза");
        weatherIconWithEmoji.put("lightssnowshowersandthunder_night", "Легкий снег и гроза");
        weatherIconWithEmoji.put("lightssnowshowersandthunder_polartwilight", "Легкий снег и гроза");
        weatherIconWithEmoji.put("partlycloudy_day", "Местами облачно\uD83C\uDF25");
        weatherIconWithEmoji.put("partlycloudy_night", "Местами облачно");
        weatherIconWithEmoji.put("partlycloudy_polartwilight", "Местами облачно");
        weatherIconWithEmoji.put("rain", "Дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("rainandthunder", "Дождь и гроза\u26C8");
        weatherIconWithEmoji.put("rainshowersandthunder_day", "Дождь и гроза\u26C8");
        weatherIconWithEmoji.put("rainshowersandthunder_night", "Дождь и гроза\u26C8");
        weatherIconWithEmoji.put("rainshowersandthunder_polartwilight", "Дождь и гроза\u26C8");
        weatherIconWithEmoji.put("rainshowers_day", "Дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("rainshowers_night", "Дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("rainshowers_polartwilight", "Дождь\uD83C\uDF27");
        weatherIconWithEmoji.put("sleet", "Мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("sleetandthunder", "Мокрый снег и гроза");
        weatherIconWithEmoji.put("sleetshowersandthunder_day", "Мокрый снег и гроза");
        weatherIconWithEmoji.put("sleetshowersandthunder_night", "Мокрый снег и гроза");
        weatherIconWithEmoji.put("sleetshowersandthunder_polartwilight", "Мокрый снег и гроза");
        weatherIconWithEmoji.put("sleetshowers_day", "Мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("sleetshowers_night", "Мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("sleetshowers_polartwilight", "Мокрый снег\uD83C\uDF28");
        weatherIconWithEmoji.put("snow", "Снег\uD83C\uDF28");
        weatherIconWithEmoji.put("snowandthunder", "Снег и гроза");
        weatherIconWithEmoji.put("snowshowersandthunder_day", "Снег и гроза");
        weatherIconWithEmoji.put("snowshowersandthunder_night", "Снег и гроза");
        weatherIconWithEmoji.put("snowshowersandthunder_polartwilight", "Снег и гроза");
        weatherIconWithEmoji.put("snowshowers_day", "Снег\uD83C\uDF28");
        weatherIconWithEmoji.put("snowshowers_night", "Снег\uD83C\uDF28");
        weatherIconWithEmoji.put("snowshowers_polartwilight", "Снег\uD83C\uDF28");

        return weatherIconWithEmoji.get(weatherIcon);
    }
}
