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
import ru.verpul.tgbot.components.TGBotButtons;
import ru.verpul.tgbot.components.TGBotMenu;

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
    private static final String HELP = "/help";


    private static final String TODAY_WEATHER_ARGUMENT = "";
    private static final String FEW_DAYS_WEATHER_ARGUMENT = "/days";
    private static final String WEEK_WEATHER_ARGUMENT = "/week";

    private static String lastKeyboardButtonPressed = null;

    public TGBotService(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            lastKeyboardButtonPressed = update.getMessage().getText();

            switch (lastKeyboardButtonPressed) {
                case START -> {
                    String username = update.getMessage().getChat().getFirstName();
                    startCommand(username);
                }
                case TGBotMenu.MAIN_MENU_WEATHER -> weatherCommand(TODAY_WEATHER_ARGUMENT);
                case HELP -> helpCommand();
                default -> unknownCommand();
            }
        } else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();
            switch (message) {
                case "weather_for_few_days" -> weatherCommand(FEW_DAYS_WEATHER_ARGUMENT);
                case "weather_for_week" -> weatherCommand(WEEK_WEATHER_ARGUMENT);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    public void sendMessage(String message) {
        SendMessage sendMessage = new SendMessage(this.botChatId, message);
        sendMessage.setParseMode("html");
        sendMessage.setReplyMarkup(TGBotMenu.getMainMenuKeyboard());

        if (lastKeyboardButtonPressed != null && lastKeyboardButtonPressed.equals("Погода")) {
            sendMessage.setReplyMarkup(TGBotButtons.inlineMarkup());
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения:  " + e.getMessage(), e);
        }
    }

    private void startCommand(String username) {
        String text = """
                Добро пожаловать, %s

                Вы можете управлять ботом через кнопки меню
                """;
        String formattedText = String.format(text, username);
        sendMessage(formattedText);
    }

    private void weatherCommand(String period) {
        String message;
        try {
            message = restTemplate.getForObject("http://localhost:9000/weather" + period, String.class);
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
                                
                Через меню вы можете узнать:
                    погоду в Мурманске
                    курс валют ЦБ
                    курс валют Тинькофф
                """;
        sendMessage(message);
    }
}
