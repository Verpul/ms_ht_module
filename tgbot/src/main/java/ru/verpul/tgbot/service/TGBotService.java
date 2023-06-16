package ru.verpul.tgbot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.verpul.tgbot.config.TGBotDataConfig;

@Component
public class TGBotService extends TelegramLongPollingBot {
    private final TGBotDataConfig tgBotDataConfig;

    public TGBotService(TGBotDataConfig tgBotConfig) {
        super(tgBotConfig.getBotToken());
        this.tgBotDataConfig = tgBotConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return tgBotDataConfig.getBotName();
    }

    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage(tgBotDataConfig.getBotChatId(), message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e);
        }
    }
}
