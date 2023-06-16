package ru.verpul.tgbot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.verpul.tgbot.config.TGBotDataConfig;

@Service
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

    @Scheduled(fixedRate = 10000)
    private void sendMessage() {
        SendMessage message = new SendMessage(tgBotDataConfig.getBotChatId(), "Test");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e);
        }
    }
}
