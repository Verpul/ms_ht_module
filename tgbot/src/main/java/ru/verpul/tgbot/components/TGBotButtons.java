package ru.verpul.tgbot.components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class TGBotButtons {
    private static final InlineKeyboardButton WEATHER_TOMORROW_BUTTON = new InlineKeyboardButton("На 3 дня");
    private static final InlineKeyboardButton WEATHER_FOR_WEEK_BUTTON = new InlineKeyboardButton("На неделю");

    public static InlineKeyboardMarkup inlineMarkup() {
        WEATHER_TOMORROW_BUTTON.setCallbackData("weather_for_few_days");
        WEATHER_FOR_WEEK_BUTTON.setCallbackData("weather_for_week");

        List<InlineKeyboardButton> rowInline = List.of(WEATHER_TOMORROW_BUTTON, WEATHER_FOR_WEEK_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
