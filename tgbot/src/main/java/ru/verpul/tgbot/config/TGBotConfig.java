package ru.verpul.tgbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.verpul.tgbot.service.TGBotService;

@Configuration
public class TGBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TGBotService tgBotService) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(tgBotService);
        return telegramBotsApi;
    }
}
