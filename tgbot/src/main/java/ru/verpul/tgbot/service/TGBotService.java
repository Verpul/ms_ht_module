package ru.verpul.tgbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TGBotService extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.chatId}")
    private String botChatId;

    @Autowired
    RestTemplate restTemplate;

    private static final String START = "/start";
    private static final String WEATHER = "/weather";
    private static final String HELP = "/help";

    public TGBotService(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String message = update.getMessage().getText();
        switch (message) {
            case START -> {
                String username = update.getMessage().getChat().getFirstName();
                startCommand(username);
            }
            case WEATHER -> weatherCommand();
            case HELP -> helpCommand();
            default -> unknownCommand();
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage(this.botChatId, message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения:  " + e.getMessage(), e);
        }
    }

    private void startCommand(String username) {
        String text = """
                Добро пожаловать в бот, %s

                Вы можете узнать погоду в Мурманске на текущий момент коммандой /weather

                Дополнительные комманды:
                /help - получение справки

                """;
        String formattedText = String.format(text, username);
        sendMessage(formattedText);
    }

    private void weatherCommand() {
        String message;
        try {
            message = restTemplate.getForObject("http://localhost:9000/weather", String.class);
        } catch (Exception e) {
            log.error("Ошибка получения данных о погоде :  " + e.getMessage(), e);
            message = "Не удалось загрузить данные о погоде. Попробуйте позже";
        }
        sendMessage(message);
    }

    private void unknownCommand() {
        String message = "Не удалось распознать команду!";
        sendMessage(message);
    }

    private void helpCommand() {
        String message = """
                Справочная информация по боту
                
                Для получения данных о погоде воспользуйтесь коммандой:
                /weather - погода на данный момент
                
                """;
        sendMessage(message);
    }
}
